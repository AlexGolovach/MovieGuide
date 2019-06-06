package com.example.android.movie.ui.main.moviedetails.details

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.movie.R
import com.example.android.movie.ui.utils.ChromeClient
import kotlinx.android.synthetic.main.item_view_video.view.*

class MovieVideosAdapter(private var items: List<String> = listOf()) :
    RecyclerView.Adapter<MovieVideosAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_view_video, parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = items[position]

        holder.bind(video)
    }

    fun setItems(list: List<String>) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetJavaScriptEnabled")
        fun bind(video: String) {
            itemView.webView.apply {
                loadData(video, "text/html", "utf-8")
                settings.javaScriptEnabled = true
                settings.allowFileAccess = true
                settings.setAppCacheEnabled(true)
                webChromeClient = ChromeClient()
            }
        }
    }
}