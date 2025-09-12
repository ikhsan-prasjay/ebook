package com.ega.ebook

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ega.ebook.databinding.ActivityStepMeditasiBinding

class StepMeditasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStepMeditasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStepMeditasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRingan.setOnClickListener {
            // Mengirim penanda "LEVEL"
            startDetailActivity("RINGAN")
        }
        binding.btnSedang.setOnClickListener {
            startDetailActivity("SEDANG")
        }
        binding.btnBerat.setOnClickListener {
            startDetailActivity("BERAT")
        }
    }

    private fun startDetailActivity(level: String) {
        val intent = Intent(this, DetailMeditasiActivity::class.java)
        intent.putExtra("LEVEL", level) // Kirim penanda level
        startActivity(intent)
    }
}