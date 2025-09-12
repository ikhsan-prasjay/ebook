package com.ega.ebook

data class Jurnal(
    val id: String? = null,
    val feeling: String? = null,
    val message: String? = null,
    val timestamp: Long? = null
) {
    // Constructor kosong diperlukan untuk Firebase
    constructor() : this("", "", "", 0)
}