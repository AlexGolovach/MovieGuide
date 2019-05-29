package com.example.android.movie.ui.main.topshows

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.movie.R
import com.example.android.network.Converter.Companion.getImageUrl
import com.example.android.network.models.shows.Show
import com.example.android.network.models.shows.ShowsList
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view_top_shows.view.*

class TopShowsAdapter(private var items: ShowsList = ShowsList(0, 0, 0, emptyList())) :
    RecyclerView.Adapter<TopShowsAdapter.ViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_top_shows, parent, false)
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            listener?.onItemClicked(items.results[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val show = items.results[position]

        holder.bind(show)
    }

    fun setItems(list: ShowsList) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.results.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(show: Show) {
            val imageUrl = show.image?.let { getImageUrl(it) }

            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(itemView.show_poster_image)

            itemView.show_title_text.text = show.title
            itemView.show_rating_text.text = show.rating.toString()
        }
    }

    interface Listener {

        fun onItemClicked(show: Show)
    }
}