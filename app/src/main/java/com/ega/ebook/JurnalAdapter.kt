package com.ega.ebook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class JurnalAdapter(private val jurnalList: List<Jurnal>) : RecyclerView.Adapter<JurnalAdapter.JurnalViewHolder>() {

    // Class untuk menampung referensi view dari item_jurnal.xml
    class JurnalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFeeling: TextView = itemView.findViewById(R.id.tvFeeling)
        val tvMessage: TextView = itemView.findViewById(R.id.tvMessage)
        val tvTimestamp: TextView = itemView.findViewById(R.id.tvTimestamp)
    }

    // Membuat ViewHolder baru (dipanggil oleh RecyclerView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JurnalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_jurnal, parent, false)
        return JurnalViewHolder(view)
    }

    // Menghubungkan data dari list ke view di ViewHolder
    override fun onBindViewHolder(holder: JurnalViewHolder, position: Int) {
        val currentJurnal = jurnalList[position]

        holder.tvFeeling.text = "Perasaan: ${currentJurnal.feeling}"
        holder.tvMessage.text = currentJurnal.message

        // Format timestamp (Long) menjadi tanggal yang mudah dibaca (String)
        currentJurnal.timestamp?.let {
            val sdf = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("id", "ID"))
            val date = Date(it)
            holder.tvTimestamp.text = sdf.format(date)
        }
    }

    // Mengembalikan jumlah total item dalam list
    override fun getItemCount(): Int {
        return jurnalList.size
    }
}