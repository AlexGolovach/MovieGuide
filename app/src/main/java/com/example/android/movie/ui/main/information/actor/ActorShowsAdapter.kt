package com.example.android.movie.ui.main.information.actor

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.movie.R
import com.example.android.network.Converter.Companion.getImageUrl
import com.example.android.network.models.shows.actorshows.ActorShows
import com.example.android.network.models.shows.actorshows.Cast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view_actor_show.view.*

class ActorShowsAdapter(private var items: ActorShows = ActorShows(emptyList(), emptyList(), 0)) :
    RecyclerView.Adapter<ActorShowsAdapter.ViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_actor_show, parent, false)
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            listener?.onItemClicked(items.cast[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val show = items.cast[position]

        holder.bind(show)
    }

    fun setItems(list: ActorShows) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.cast.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(show: Cast) {
            val imageUrl = show.image?.let { getImageUrl(it) }

            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(itemView.poster_show_image)

            itemView.show_title_text.text = show.original_name
            itemView.show_rating_text.text = show.rating.toString()
        }
    }

    interface Listener {

        fun onItemClicked(show: Cast)
    }
}