package com.ega.ebook

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ega.ebook.databinding.ActivityLoginBinding
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("users")

        binding.btnLogin.setOnClickListener {
            val phone = binding.etPhone.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Nomor Telepon dan Password harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(phone, password)
            }
        }

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(phone: String, password: String) {
        database.child(phone).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(User::class.java)
                    if (user?.password == password) {
                        // Login berhasil
                        Toast.makeText(this@LoginActivity, "Login berhasil!", Toast.LENGTH_SHORT).show()

                        // Periksa peran (role) pengguna
                        if (user.role == "admin") {
                            // Jika admin, arahkan ke AdminActivity
                            val intent = Intent(this@LoginActivity, AdminActivity::class.java).apply {
                                putExtra("USER_NAME", user.username)
                            }
                            startActivity(intent)
                        } else {
                            // Jika user biasa, arahkan ke MainActivity
                            val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
                                putExtra("USER_NAME", user.username)
                            }
                            startActivity(intent)
                        }
                        finish() // Tutup LoginActivity
                    } else {
                        // Password salah
                        Toast.makeText(this@LoginActivity, "Password salah.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Pengguna tidak ditemukan
                    Toast.makeText(this@LoginActivity, "Pengguna dengan nomor ini tidak ditemukan.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@LoginActivity, "Gagal login: ${error.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
