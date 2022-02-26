package com.example.perludilindungi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.perludilindungi.R
import com.example.perludilindungi.models.DataFaskesResponse
import com.example.perludilindungi.models.FaskesResponse

class FaskesAdapter: RecyclerView.Adapter<FaskesAdapter.FaskesViewHolder>() {

    private var faskesList = emptyList<DataFaskesResponse>()

    inner class FaskesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaskesViewHolder {
        return FaskesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.faskes_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return faskesList.size
    }

    override fun onBindViewHolder(holder: FaskesViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.tvNamaFaskes).text = faskesList[position].nama
        holder.itemView.findViewById<TextView>(R.id.tvAlamatFaskes).text = faskesList[position].alamat
        holder.itemView.findViewById<TextView>(R.id.tvNoTelp).text = faskesList[position].telp
        holder.itemView.findViewById<TextView>(R.id.tvJenisFaskes).text = faskesList[position].jenis_faskes
        holder.itemView.findViewById<TextView>(R.id.tvKodeFaskes).text = faskesList[position].kode
    }

    fun setData(newList: List<DataFaskesResponse>) {
        faskesList = newList
        notifyDataSetChanged()
    }
}