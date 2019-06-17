package com.example.android.movie.ui.main.details.movie

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.database.model.Video
import com.example.android.movie.R
import kotlinx.android.synthetic.main.item_view_video.view.*

class MovieVideosAdapter(private var items: List<Video> = listOf()) :
    RecyclerView.Adapter<MovieVideosAdapter.ViewHolder>() {

    var listener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_video, parent, false)
        val holder = ViewHolder(view)

        holder.itemView.videoTitleText.setOnClickListener {
            listener?.onItemClicked(items[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = items[position]

        holder.bind(video)
    }

    fun setItems(list: List<Video>) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(video: Video) {
            itemView.videoTitleText.text = video.title
        }
    }

    interface OnClickListener {
        fun onItemClicked(video: Video)
    }
}