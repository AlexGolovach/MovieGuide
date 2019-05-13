package com.example.android.movie.ui.main.actor

import ImageLoader
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.imageloader.Callback
import com.example.android.movie.R
import com.example.android.network.getImageUrl
import com.example.android.network.models.movie.Movie
import kotlinx.android.synthetic.main.item_view_actor_movie.view.*

class ActorMovieAdapter(private var items: List<Movie> = listOf()) :
    RecyclerView.Adapter<ActorMovieAdapter.ViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ActorMovieAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_actor_movie, parent, false)
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            listener?.onItemClicked(items[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ActorMovieAdapter.ViewHolder, position: Int) {
        val movie = items[position]

        holder.bind(movie)
    }

    fun setItems(list: List<Movie>) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            val imageUrl = getImageUrl(movie.image)

            ImageLoader.getInstance()?.load(imageUrl, object : Callback {
                override fun onSuccess(url: String, bitmap: Bitmap) {
                    if (imageUrl == url) {
                        itemView.poster_movie_image.background = null
                        itemView.poster_movie_image.setImageBitmap(bitmap)
                    }
                }

                override fun onError(url: String, throwable: Throwable) {
                    if (imageUrl == url) {
                        itemView.poster_movie_image.setImageResource(R.drawable.image_placeholder)
                    }
                }
            })

            itemView.movie_title_text.text = movie.title
            itemView.movie_rating_text.text = movie.rating.toString()
        }
    }

    interface Listener {

        fun onItemClicked(movie: Movie)
    }
}