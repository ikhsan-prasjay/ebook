package com.ega.ebook

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ega.ebook.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userName = intent.getStringExtra("USER_NAME") ?: "Admin"
        binding.tvAdminWelcome.text = "Selamat Datang, $userName!"

        // Tambahkan aksi untuk tombol-tombol admin
        binding.btnUbahTampilan.setOnClickListener {
            Toast.makeText(this, "Fitur ubah tampilan belum tersedia", Toast.LENGTH_SHORT).show()
        }

        binding.btnKelolaFitur.setOnClickListener {
            Toast.makeText(this, "Fitur kelola meditasi belum tersedia", Toast.LENGTH_SHORT).show()
        }
    }
}
