package com.ega.ebook

// Data class untuk menyimpan informasi pengguna di Firebase
data class User(
    val username: String? = null,
    val phone: String? = null,
    val password: String? = null, // Password disimpan untuk verifikasi login
    val role: String? = "user"    // Default role adalah "user"
) {
    // Constructor kosong diperlukan oleh Firebase
    constructor() : this("", "", "", "user")
}
