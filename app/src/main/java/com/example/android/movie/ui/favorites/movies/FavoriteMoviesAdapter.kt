package com.example.android.movie.ui.favorites.movies

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.database.model.FavoriteMovies
import com.example.android.movie.R
import com.example.android.network.Converter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view_favorite_movie.view.*

class FavoriteMoviesAdapter(private var items: ArrayList<FavoriteMovies> = ArrayList()) :
    RecyclerView.Adapter<FavoriteMoviesAdapter.ViewHolder>() {

    var openListener: OpenListener? = null
    var deleteListener: DeleteListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_favorite_movie, parent, false)
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            val movie = items[holder.adapterPosition]

            openListener?.onOpenClikedListener(movie)
        }

        holder.itemView.icon_delete.setOnClickListener {
            deleteListener?.onDeleteClickedListener(items[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = items[position]

        Picasso.get()
            .load(Converter.getImageUrl(movie.poster))
            .placeholder(R.drawable.image_placeholder)
            .error(R.drawable.image_placeholder)
            .into(holder.itemView.film_poster_image)

        holder.itemView.film_title_text.text = movie.movie
        holder.itemView.movie_rating_text.text = movie.rating.toString()
    }

    fun setItems(list: ArrayList<FavoriteMovies>) {
        items.clear()
        items.addAll(list)

        notifyDataSetChanged()
    }

    fun deleteItems(movie: FavoriteMovies) {
        val iterator = items.iterator()

        while (iterator.hasNext()) {
            val item = iterator.next()

            if (movie == item) {
                iterator.remove()
            }
        }

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OpenListener {
        fun onOpenClikedListener(movie: FavoriteMovies)
    }

    interface DeleteListener {
        fun onDeleteClickedListener(movie: FavoriteMovies)
    }
}