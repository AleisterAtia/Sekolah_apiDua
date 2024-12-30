package com.fadhiil2010.sekolah_api.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fadhiil2010.sekolah_api.DetailSekolah
import com.fadhiil2010.sekolah_api.Model.SekolahResponse
import com.fadhiil2010.sekolah_api.R
import com.squareup.picasso.Picasso

class SekolahAdapter (
    val dataSekolah: ArrayList<SekolahResponse.ListItem>
): RecyclerView.Adapter<SekolahAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)  {
        val imgSekolah = view.findViewById<ImageView>(R.id.imgSekolah)
        val tvNamaSekolah = view.findViewById<TextView>(R.id.tvNamaSekolah)
        val tvNoTlp = view.findViewById<TextView>(R.id.tvNoTlp)
        val tvAkreditasi = view.findViewById<TextView>(R.id.tvAkreditasi)
        val tvAkreditasiHuruf = view.findViewById<TextView>(R.id.tvAkreditasiHuruf)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sekolah_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hasilResponse = dataSekolah[position]
        Picasso.get().load(hasilResponse.gambar).into(holder.imgSekolah)
        holder.tvNamaSekolah.text = hasilResponse.nama_sekolah
        holder.tvNoTlp.text = hasilResponse.notlp
        holder.tvAkreditasiHuruf.text = hasilResponse.akreditasi

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailSekolah::class.java).apply {
                putExtra("gambar", hasilResponse.gambar)
                putExtra("nama_sekolah", hasilResponse.nama_sekolah)
                putExtra("notlp", hasilResponse.notlp)
                putExtra("akreditasi", hasilResponse.akreditasi)
                putExtra("informasi", hasilResponse.informasi)
            }

            holder.imgSekolah.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataSekolah.size
    }

    fun setData(data: List<SekolahResponse.ListItem>){
        dataSekolah.clear()
        dataSekolah.addAll(data)
    }

}