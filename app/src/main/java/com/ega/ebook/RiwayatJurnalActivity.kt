package com.ega.ebook

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ega.ebook.databinding.ActivityRiwayatJurnalBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RiwayatJurnalActivity : AppCompatActivity() {

    // Binding untuk mengakses komponen layout XML
    private lateinit var binding: ActivityRiwayatJurnalBinding
    // Referensi ke Firebase Realtime Database
    private lateinit var database: DatabaseReference
    // List untuk menampung data jurnal
    private lateinit var jurnalList: MutableList<Jurnal>
    // Adapter untuk RecyclerView
    private lateinit var adapter: JurnalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatJurnalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi RecyclerView
        binding.rvJurnal.layoutManager = LinearLayoutManager(this)
        binding.rvJurnal.setHasFixedSize(true) // Optimasi jika ukuran item tetap

        // Inisialisasi list dan adapter
        jurnalList = mutableListOf()
        adapter = JurnalAdapter(jurnalList)
        binding.rvJurnal.adapter = adapter

        // Panggil fungsi untuk mengambil data dari Firebase
        fetchJurnalsFromFirebase()
    }

    private fun fetchJurnalsFromFirebase() {
        // Tampilkan progress bar saat loading
        binding.progressBar.visibility = View.VISIBLE
        binding.rvJurnal.visibility = View.GONE
        binding.tvEmpty.visibility = View.GONE

        // Tentukan path di Firebase ("jurnals")
        database = FirebaseDatabase.getInstance().getReference("jurnals")

        // Tambahkan listener untuk membaca data
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Hapus data lama agar tidak duplikat
                jurnalList.clear()

                if (snapshot.exists()) {
                    // Looping semua data di dalam "jurnals"
                    for (jurnalSnap in snapshot.children) {
                        val jurnalData = jurnalSnap.getValue(Jurnal::class.java)
                        jurnalData?.let {
                            jurnalList.add(it)
                        }
                    }

                    // Balik urutan list agar data terbaru tampil di paling atas
                    jurnalList.reverse()

                    // Beri tahu adapter bahwa data telah berubah
                    adapter.notifyDataSetChanged()

                    // Tampilkan RecyclerView dan sembunyikan pesan kosong
                    binding.rvJurnal.visibility = View.VISIBLE
                    binding.tvEmpty.visibility = View.GONE
                } else {
                    // Jika tidak ada data, tampilkan pesan kosong
                    binding.rvJurnal.visibility = View.GONE
                    binding.tvEmpty.visibility = View.VISIBLE
                }

                // Sembunyikan progress bar setelah selesai
                binding.progressBar.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                // Sembunyikan progress bar
                binding.progressBar.visibility = View.GONE

                // Tampilkan pesan error jika gagal mengambil data
                Toast.makeText(this@RiwayatJurnalActivity, "Gagal memuat data: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}