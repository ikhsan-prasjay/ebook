package com.ega.ebook

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ega.ebook.databinding.ActivityRegisterBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi referensi database ke node "users"
        database = FirebaseDatabase.getInstance().getReference("users")

        binding.btnDaftar.setOnClickListener {
            val username = binding.etNamaPengguna.text.toString().trim()
            val phone = binding.etNomorTelepon.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                // Simpan pengguna baru ke Firebase
                saveUserToFirebase(username, phone, password)
            }
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun saveUserToFirebase(username: String, phone: String, password: String) {
        // Buat objek User baru dengan role default "user"
        val user = User(username, phone, password, "user")

        // Gunakan nomor telepon sebagai key unik (penyederhanaan)
        database.child(phone).setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                // Pindah ke MainActivity
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("USER_NAME", username)
                }
                startActivity(intent)
                finishAffinity()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Registrasi gagal: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}
