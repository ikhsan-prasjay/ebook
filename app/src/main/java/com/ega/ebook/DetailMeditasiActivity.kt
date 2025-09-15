package com.ega.ebook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ega.ebook.databinding.ActivityDetailMeditasiBinding

class DetailMeditasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMeditasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMeditasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false) // Sembunyikan judul default
        supportActionBar?.setDisplayHomeAsUpEnabled(true)   // Tampilkan tombol kembali
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Aksi saat tombol kembali ditekan
        }

        // Ambil penanda level yang dikirim
        val level = intent.getStringExtra("LEVEL")

        // Tampilkan konten berdasarkan level
        when (level) {
            "RINGAN" -> {
                binding.tvTitle.text = "Meditasi Ringan"
                // Memisahkan konten dan efek dari strings.xml
                val fullContent = getString(R.string.meditasi_ringan_content).split("👉")
                binding.tvContent.text = fullContent.getOrElse(0) { "" }.trim()
                binding.tvEffect.text = "👉${fullContent.getOrElse(1) { "" }}".trim()
            }
            "SEDANG" -> {
                binding.tvTitle.text = "Meditasi Sedang"
                val fullContent = getString(R.string.meditasi_sedang_content).split("👉")
                binding.tvContent.text = fullContent.getOrElse(0) { "" }.trim()
                binding.tvEffect.text = "👉${fullContent.getOrElse(1) { "" }}".trim()
            }
            "BERAT" -> {
                binding.tvTitle.text = "Meditasi Berat"
                val fullContent = getString(R.string.meditasi_berat_content).split("👉")
                binding.tvContent.text = fullContent.getOrElse(0) { "" }.trim()
                binding.tvEffect.text = "👉${fullContent.getOrElse(1) { "" }}".trim()
            }
            else -> {
                // Fallback jika terjadi error
                binding.tvTitle.text = "Meditasi"
                binding.tvContent.text = "Konten tidak ditemukan."
                binding.tvEffect.text = ""
            }
        }
    }
}