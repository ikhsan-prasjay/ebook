package com.ega.ebook

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ega.ebook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardMeditasi.setOnClickListener {
            startActivity(Intent(this, MeditasiActivity::class.java))
        }

        binding.cardKonsultasi.setOnClickListener {
            openWhatsApp()
        }

        binding.cardJurnal.setOnClickListener {
            showJurnalDialog()
        }

        binding.cardStep.setOnClickListener {
            startActivity(Intent(this, StepMeditasiActivity::class.java))
        }

        binding.cardEbook.setOnClickListener {
            startActivity(Intent(this, EbookActivity::class.java))
        }
    }

    private fun openWhatsApp() {
        val phoneNumber = "6282322765137"
        val url = "https://api.whatsapp.com/send?phone=$phoneNumber"
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "WhatsApp tidak terinstall.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showJurnalDialog() {
        val options = arrayOf("Buat Jurnal Baru", "Lihat Riwayat Jurnal")
        AlertDialog.Builder(this)
            .setTitle("Menu Jurnal")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> startActivity(Intent(this, JurnalActivity::class.java))
                    1 -> startActivity(Intent(this, RiwayatJurnalActivity::class.java))
                }
            }
            .create()
            .show()
    }
}