package com.example.android.movie.ui.main.details.movie

import ImageLoader
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.database.model.Movie
import com.example.android.movie.R
import kotlinx.android.synthetic.main.item_view_recommended_movie.view.*
import java.lang.ref.WeakReference

class RecommendedMoviesAdapter(private var items: List<Movie> = emptyList()) :
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
            listener?.onItemClicked(items[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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

            ImageLoader.getInstance()?.load(movie.image, WeakReference(itemView.posterMovieImage))
            itemView.movieTitleText.text = movie.title
            itemView.movieRatingText.text = movie.rating.toString()
        }
    }

    interface Listener {

        fun onItemClicked(movie: Movie)
    }
}