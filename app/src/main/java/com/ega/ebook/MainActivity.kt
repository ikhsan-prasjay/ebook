package com.ega.ebook

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ega.ebook.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Ambil nama dari Intent, berikan nilai default "Sobat" jika tidak ada
        val userName = intent.getStringExtra("USER_NAME") ?: "Sobat"

        // 2. Atur teks sapaan dan tanggal di header
        setupHeader(userName)

        // 3. Atur listener untuk setiap kartu menu agar bisa diklik
        setupMenuListeners()
    }

    private fun setupHeader(userName: String) {
        // Menentukan sapaan berdasarkan waktu saat ini (Pagi, Siang, Sore, Malam)
        val greeting = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> "Selamat Pagi"
            in 12..15 -> "Selamat Siang"
            in 16..18 -> "Selamat Sore"
            else -> "Selamat Malam"
        }
        binding.tvGreeting.text = "$greeting, $userName"

        // Mengatur format dan menampilkan tanggal hari ini
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        binding.tvDate.text = sdf.format(Date())
    }

    private fun setupMenuListeners() {
        // Menambahkan aksi klik untuk setiap kartu menu
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
        val phoneNumber = "6282322765137" // Ganti dengan nomor tujuan
        val url = "https://api.whatsapp.com/send?phone=$phoneNumber"
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }
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