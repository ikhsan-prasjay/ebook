package com.ega.ebook

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ega.ebook.databinding.ActivityKelolaFiturBinding
import com.google.firebase.database.*

class KelolaFiturActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKelolaFiturBinding
    private lateinit var settingsRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelolaFiturBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingsRef = FirebaseDatabase.getInstance().getReference("settings")

        loadCurrentFeatureSettings()

        binding.btnSimpanFitur.setOnClickListener {
            saveFeatureSettings()
        }
    }

    private fun loadCurrentFeatureSettings() {
        settingsRef.child("isMeditasiBeratEnabled").addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val isEnabled = snapshot.getValue(Boolean::class.java) ?: true
                binding.switchMeditasiBerat.isChecked = isEnabled
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun saveFeatureSettings() {
        val isMeditasiBeratEnabled = binding.switchMeditasiBerat.isChecked
        settingsRef.child("isMeditasiBeratEnabled").setValue(isMeditasiBeratEnabled)
            .addOnSuccessListener {
                Toast.makeText(this, "Pengaturan fitur disimpan!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal menyimpan pengaturan.", Toast.LENGTH_SHORT).show()
            }
    }
}
