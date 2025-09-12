package com.ega.ebook

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ega.ebook.databinding.ActivityJurnalBinding
import com.google.firebase.database.FirebaseDatabase

class JurnalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJurnalBinding
    private val database = FirebaseDatabase.getInstance().getReference("jurnals")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJurnalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Setup Spinner, Button OnClickListener untuk save ke Firebase
    }
    // ... (kode lengkap sama seperti contoh sebelumnya) ...
}