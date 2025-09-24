package com.ega.ebook

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ega.ebook.databinding.ActivityUbahTampilanBinding
import com.google.firebase.database.*

class UbahTampilanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUbahTampilanBinding
    private lateinit var settingsRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUbahTampilanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Referensi ke node "settings" di Firebase
        settingsRef = FirebaseDatabase.getInstance().getReference("settings")

        loadCurrentSettings()

        binding.btnSimpanTema.setOnClickListener {
            saveThemeSelection()
        }
    }

    private fun loadCurrentSettings() {
        settingsRef.child("themeColor").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                when (snapshot.getValue(String::class.java)) {
                    "dark" -> binding.rbDark.isChecked = true
                    "ocean" -> binding.rbOcean.isChecked = true
                    else -> binding.rbDefault.isChecked = true
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun saveThemeSelection() {
        val selectedTheme = when (binding.rgTema.checkedRadioButtonId) {
            R.id.rbDark -> "dark"
            R.id.rbOcean -> "ocean"
            else -> "default"
        }

        settingsRef.child("themeColor").setValue(selectedTheme)
            .addOnSuccessListener {
                Toast.makeText(this, "Tema berhasil disimpan!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal menyimpan tema.", Toast.LENGTH_SHORT).show()
            }
    }
}
