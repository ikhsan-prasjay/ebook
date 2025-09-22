package com.ega.ebook

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.ega.ebook.databinding.ActivityRiwayatJurnalBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Locale

class RiwayatJurnalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRiwayatJurnalBinding
    private lateinit var database: DatabaseReference
    private lateinit var jurnalList: MutableList<Jurnal>
    private lateinit var originalJurnalList: MutableList<Jurnal> // Simpan daftar asli
    private lateinit var adapter: JurnalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatJurnalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        binding.rvJurnal.layoutManager = LinearLayoutManager(this)
        binding.rvJurnal.setHasFixedSize(true)

        jurnalList = mutableListOf()
        originalJurnalList = mutableListOf()
        adapter = JurnalAdapter(jurnalList)
        binding.rvJurnal.adapter = adapter

        fetchJurnalsFromFirebase()
        setupSearchView()
    }

    private fun fetchJurnalsFromFirebase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvJurnal.visibility = View.GONE
        binding.tvEmpty.visibility = View.GONE

        database = FirebaseDatabase.getInstance().getReference("jurnals")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                jurnalList.clear()
                originalJurnalList.clear() // Bersihkan juga daftar asli

                if (snapshot.exists()) {
                    for (jurnalSnap in snapshot.children) {
                        val jurnalData = jurnalSnap.getValue(Jurnal::class.java)
                        jurnalData?.let {
                            // Menambahkan ke posisi pertama agar tidak perlu reverse()
                            originalJurnalList.add(0, it)
                        }
                    }
                    // Salin daftar asli ke daftar yang akan ditampilkan
                    jurnalList.addAll(originalJurnalList)
                    adapter.notifyDataSetChanged()

                    binding.rvJurnal.visibility = View.VISIBLE
                    binding.tvEmpty.visibility = View.GONE
                } else {
                    binding.rvJurnal.visibility = View.GONE
                    binding.tvEmpty.visibility = View.VISIBLE
                }

                binding.progressBar.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this@RiwayatJurnalActivity, "Gagal memuat data: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Tidak perlu aksi khusus saat submit, karena filter berjalan realtime
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Panggil fungsi filter setiap kali teks pencarian berubah
                filterJurnals(newText)
                return true
            }
        })
    }

    /**
     * Fungsi untuk memfilter daftar jurnal berdasarkan query pencarian.
     */
    private fun filterJurnals(query: String?) {
        // 1. Tentukan daftar yang akan ditampilkan berdasarkan query
        val filteredList = if (query.isNullOrBlank()) {
            // Jika query kosong, tampilkan semua jurnal asli
            originalJurnalList
        } else {
            // Jika ada query, filter daftar asli
            val lowerCaseQuery = query.trim().toLowerCase(Locale.ROOT)
            originalJurnalList.filter { jurnal ->
                // Cek apakah 'feeling' atau 'message' mengandung query (case-insensitive)
                val feelingMatch = jurnal.feeling?.toLowerCase(Locale.ROOT)?.contains(lowerCaseQuery) == true
                val messageMatch = jurnal.message?.toLowerCase(Locale.ROOT)?.contains(lowerCaseQuery) == true
                feelingMatch || messageMatch
            }
        }

        // 2. Perbarui daftar yang terhubung ke adapter
        jurnalList.clear()
        jurnalList.addAll(filteredList)

        // 3. Beri tahu adapter bahwa data telah berubah untuk me-refresh tampilan
        adapter.notifyDataSetChanged()
    }
}