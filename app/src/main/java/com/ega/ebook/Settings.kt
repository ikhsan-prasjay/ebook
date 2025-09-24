package com.ega.ebook

// Data class untuk menyimpan pengaturan aplikasi di Firebase
data class Settings(
    val themeColor: String? = "default", // Contoh: "default", "dark", "ocean"
    val isMeditasiBeratEnabled: Boolean? = true
) {
    // Constructor kosong diperlukan oleh Firebase
    constructor() : this("default", true)
}
