package com.ega.ebook

import android.app.Application
import com.google.firebase.FirebaseApp

// Kelas ini akan berjalan pertama kali saat aplikasi dibuka
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Inisialisasi Firebase untuk seluruh aplikasi
        // Ini adalah kunci untuk memperbaiki error Anda
        FirebaseApp.initializeApp(this)
    }
}