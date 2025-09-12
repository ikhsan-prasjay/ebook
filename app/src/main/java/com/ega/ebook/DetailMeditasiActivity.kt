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

        // Ambil penanda level yang dikirim dari activity sebelumnya
        val level = intent.getStringExtra("LEVEL")

        // Gunakan 'when' untuk menentukan konten mana yang akan ditampilkan
        when (level) {
            "RINGAN" -> {
                binding.tvTitle.text = "1. Meditasi Ringan"
                binding.tvContent.text = getString(R.string.meditasi_ringan_content)
            }
            "SEDANG" -> {
                binding.tvTitle.text = "2. Meditasi Sedang"
                binding.tvContent.text = getString(R.string.meditasi_sedang_content)
            }
            "BERAT" -> {
                binding.tvTitle.text = "3. Meditasi Berat"
                binding.tvContent.text = getString(R.string.meditasi_berat_content)
            }
            else -> {
                // Fallback jika terjadi error
                binding.tvTitle.text = "Meditasi"
                binding.tvContent.text = "Konten tidak ditemukan."
            }
        }
    }
}