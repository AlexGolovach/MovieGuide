package com.example.android.movie.ui.main.information.details.movie

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.movie.R
import com.example.android.network.Converter.Companion.getImageUrl
import com.example.android.network.models.movie.Movie
import com.example.android.network.models.movie.MovieList
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view_recommended_movie.view.*

class RecommendedMoviesAdapter(private var items: MovieList = MovieList(0, 0, 0, emptyList())) :
    RecyclerView.Adapter<RecommendedMoviesAdapter.ViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        p1: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_recommended_movie, parent, false)
        val holder =
            ViewHolder(
                view
            )

        holder.itemView.setOnClickListener {
            listener?.onItemClicked(items.results[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = items.results[position]

        holder.bind(movie)
    }

    fun setItems(list: MovieList) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.results.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
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

        fun onItemClicked(movie: Movie)
    }
}