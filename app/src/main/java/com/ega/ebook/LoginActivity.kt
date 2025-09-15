package com.ega.ebook

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ega.ebook.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val phone = binding.etPhone.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Nomor Telepon dan Password harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                // Di sini Anda bisa menambahkan logika verifikasi login
                // Untuk saat ini, kita akan langsung masuk ke MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.tvRegister.setOnClickListener {
            // Arahkan ke halaman Register
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}