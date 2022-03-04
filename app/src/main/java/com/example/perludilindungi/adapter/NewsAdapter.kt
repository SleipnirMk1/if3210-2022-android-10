package com.example.perludilindungi.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.perludilindungi.R
import com.example.perludilindungi.models.NewsResultResponse

class NewsAdapter(private var results: ArrayList<NewsResultResponse>, private var itemNumber: Int) :
    RecyclerView.Adapter<NewsAdapter.ListViewHolder>() {

    private lateinit var onClickCallback: OnClickCallback

    interface OnClickCallback {
        fun onClick(data: NewsResultResponse)
    }

    fun setOnClickListener(onClickCallback: OnClickCallback) {
        this.onClickCallback = onClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        Log.d("News", "onCreateViewHolder: called")
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val result = results[position]

        holder.newsTitle.text = result.title
        holder.newsDate.text = result.pubDate

        Glide.with(holder.itemView.context)
            .load(result.encl.imageUrl)
            .into(holder.newsImage)

        holder.itemView.setOnClickListener { onClickCallback.onClick(results[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = itemNumber

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var newsTitle: TextView = itemView.findViewById(R.id.news_title)
        var newsDate: TextView = itemView.findViewById(R.id.news_date)
        var newsImage: ImageView = itemView.findViewById(R.id.thumbnail)
    }

    fun setResultsData(data: ArrayList<NewsResultResponse>) {
        this.results.clear()
        this.results.addAll(data)
        notifyDataSetChanged()
    }

    fun setItemCount(data: Int) {
        itemNumber = data
        notifyDataSetChanged()
    }

}
