package com.ega.ebook

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ega.ebook.databinding.ActivityJurnalBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class JurnalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJurnalBinding
    // 1. Deklarasikan 'database' di sini tanpa langsung mengisinya
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJurnalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 2. Inisialisasi database di dalam onCreate, setelah semuanya siap
        database = FirebaseDatabase.getInstance().getReference("jurnals")

        // Panggil fungsi untuk setup spinner dan tombol
        setupSpinner()
        setupSaveButton()
    }

    private fun setupSpinner() {
        val feelings = listOf("Senang", "Sedih", "Biasa", "Marah")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, feelings)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFeeling.adapter = adapter
    }

    private fun setupSaveButton() {
        binding.btnSimpanJurnal.setOnClickListener {
            saveJurnalToFirebase()
        }
    }

    private fun saveJurnalToFirebase() {
        val feeling = binding.spinnerFeeling.selectedItem.toString()
        val message = binding.etJurnalMessage.text.toString().trim()

        if (message.isEmpty()) {
            Toast.makeText(this, "Pesan tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }

        // Buat ID unik untuk setiap entri jurnal
        val jurnalId = database.push().key ?: ""
        val timestamp = System.currentTimeMillis()

        // Buat objek Jurnal
        val jurnal = Jurnal(jurnalId, feeling, message, timestamp)

        // Simpan objek ke Firebase
        database.child(jurnalId).setValue(jurnal)
            .addOnSuccessListener {
                Toast.makeText(this, "Jurnal berhasil disimpan", Toast.LENGTH_SHORT).show()
                finish() // Tutup activity setelah berhasil menyimpan
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal menyimpan jurnal: ${it.message}", Toast.LENGTH_LONG).show()
            }
    }
}