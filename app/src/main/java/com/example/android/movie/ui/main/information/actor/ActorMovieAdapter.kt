package com.example.android.movie.ui.main.information.actor

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.movie.R
import com.example.android.network.Converter.Companion.getImageUrl
import com.example.android.network.models.movie.actormovies.ActorMovies
import com.example.android.network.models.movie.actormovies.Cast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view_actor_movie.view.*

class ActorMovieAdapter(private var items: ActorMovies = ActorMovies(emptyList(), emptyList(),0)) :
    RecyclerView.Adapter<ActorMovieAdapter.ViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ActorMovieAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_actor_movie, parent, false)
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            listener?.onItemClicked(items.cast[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ActorMovieAdapter.ViewHolder, position: Int) {
        val movie = items.cast[position]

        holder.bind(movie)
    }

    fun setItems(list: ActorMovies) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.cast.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Cast) {
            val imageUrl = movie.image?.let { getImageUrl(it) }

            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(itemView.poster_movie_image)

            itemView.movie_title_text.text = movie.title
            itemView.movie_rating_text.text = movie.rating.toString()
        }
    }

    interface Listener {

        fun onItemClicked(movie: Cast)
    }
}