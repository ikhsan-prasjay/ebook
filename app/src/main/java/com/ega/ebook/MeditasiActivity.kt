package com.ega.ebook

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.ega.ebook.databinding.ActivityMeditasiBinding

class MeditasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMeditasiBinding

    // Handler untuk menjalankan timer di UI thread
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    // Variabel untuk mengelola state timer
    private var countdownValue = 4
    private var cycle = 0 // 0 = Tarik Napas, 1 = Tahan, 2 = Buang Napas
    private var isTimerRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeditasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tombol Mulai: hanya bisa ditekan jika timer tidak sedang berjalan
        binding.btnStart.setOnClickListener {
            if (!isTimerRunning) {
                startTimer()
            }
        }

        // Tombol Reset: menghentikan dan mengembalikan ke state awal
        binding.btnReset.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        isTimerRunning = true
        binding.btnStart.isEnabled = false // Nonaktifkan tombol Mulai saat timer berjalan
        binding.btnReset.isEnabled = true  // Aktifkan tombol Reset

        // Definisikan tugas yang akan dijalankan berulang kali oleh Handler
        runnable = Runnable {
            // Update angka timer di UI
            binding.tvTimer.text = countdownValue.toString()

            // Cek instruksi berdasarkan siklus saat ini
            updateInstruction()

            // Kurangi hitungan
            countdownValue--

            // Jika hitungan mencapai 0, reset hitungan ke 4 dan lanjut ke siklus berikutnya
            if (countdownValue < 0) {
                countdownValue = 4 // Reset hitungan
                cycle = (cycle + 1) % 3 // Pindah siklus (0, 1, 2, 0, 1, ...)
                updateInstruction() // Update instruksi untuk siklus baru
                binding.tvTimer.text = countdownValue.toString() // Tampilkan angka 4 lagi
                countdownValue--
            }

            // Jalankan kembali runnable ini setelah 1 detik
            handler.postDelayed(runnable, 1000)
        }

        // Mulai jalankan runnable untuk pertama kali
        handler.post(runnable)
    }

    private fun updateInstruction() {
        when (cycle) {
            0 -> binding.tvInstruction.text = "Tarik Napas"
            1 -> binding.tvInstruction.text = "Tahan"
            2 -> binding.tvInstruction.text = "Buang Napas"
        }
    }

    private fun stopTimer() {
        if (::runnable.isInitialized) {
            handler.removeCallbacks(runnable)
        }
        isTimerRunning = false
        binding.btnStart.isEnabled = true // Aktifkan kembali tombol Mulai
    }

    private fun resetTimer() {
        stopTimer()
        countdownValue = 4
        cycle = 0
        binding.tvTimer.text = "4"
        binding.tvInstruction.text = "Tekan Mulai Untuk Memulai"
    }

    override fun onDestroy() {
        super.onDestroy()
        // Pastikan timer berhenti saat activity dihancurkan untuk mencegah memory leak
        stopTimer()
    }
}