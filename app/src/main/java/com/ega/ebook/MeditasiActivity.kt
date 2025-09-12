package com.ega.ebook

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.ega.ebook.databinding.ActivityMeditasiBinding

class MeditasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMeditasiBinding
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private var countdownValue = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeditasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTimer()
        startTimer()

        binding.btnReset.setOnClickListener {
            stopTimer()
            countdownValue = 4
            startTimer()
        }
    }

    private fun setupTimer() {
        runnable = Runnable {
            binding.tvTimer.text = countdownValue.toString()
            countdownValue--
            if (countdownValue < 1) {
                countdownValue = 4
            }
            handler.postDelayed(runnable, 1000)
        }
    }

    private fun startTimer() {
        handler.post(runnable)
    }

    private fun stopTimer() {
        handler.removeCallbacks(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer() // Mencegah memory leak
    }
}