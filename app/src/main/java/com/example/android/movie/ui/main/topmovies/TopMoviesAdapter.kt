package com.example.android.movie.ui.main.topmovies

import ImageLoader
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.android.movie.App
import com.example.android.movie.R
import com.example.android.network.Converter.getImageUrl
import com.example.android.network.models.movie.Movie
import com.example.android.network.models.movie.MovieList
import kotlinx.android.synthetic.main.item_view_top_films.view.*
import java.lang.ref.WeakReference


class TopMoviesAdapter(private var items: ArrayList<Movie> = ArrayList()) :
    RecyclerView.Adapter<TopMoviesAdapter.ViewHolder>() {

    var listener: Listener? = null
    var loadListener: LoadItems? = null

    private var page: Int? = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_top_films, parent, false)
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            listener?.onItemClicked(items[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = items[position]

        if (position == itemCount - 1) {
            page?.let { loadListener?.onLoadItems(it.plus(1)) }
        }

        holder.bind(movie)
    }

//    fun setItems(list: MovieList) {
//        page = list.page
//
//        for (i in 0 until list.results.size) {
//            items.add(list.results[i])
//        }
//        notifyDataSetChanged()
//    }

    fun updateItems(movies: MovieList) {
        page = movies.page

        for (i in 0 until movies.results.size) {
            items.add(movies.results[i])
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            val imageUrl = movie.image?.let { getImageUrl(it) }

            ImageLoader.getInstance()?.load(imageUrl, WeakReference(itemView.filmPosterImage))
            itemView.filmTitleText.text = movie.title
            itemView.movieRatingText.text = movie.rating.toString()
        }
    }

    interface Listener {

        fun onItemClicked(movie: Movie)
    }

    interface LoadItems {

        fun onLoadItems(page: Int)
    }
}