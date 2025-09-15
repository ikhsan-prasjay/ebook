package com.ega.ebook

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ega.ebook.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDaftar.setOnClickListener {
            val username = binding.etNamaPengguna.text.toString().trim()
            val phone = binding.etNomorTelepon.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()

                // Pindah ke MainActivity sambil mengirim nama pengguna
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("USER_NAME", username)
                }
                startActivity(intent)
                finishAffinity() // Menutup semua activity sebelumnya
            }
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}