package com.example.android.movie.ui.main.topmovies

import ImageLoader
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.movie.R
import com.example.android.network.Converter.getImageUrl
import com.example.android.network.models.movie.Movie
import kotlinx.android.synthetic.main.item_view_search_result.view.*
import java.lang.ref.WeakReference

class SearchResultMoviesAdapter(private var items: List<Movie> = listOf()) :
    RecyclerView.Adapter<SearchResultMoviesAdapter.ViewHolder>() {

    var openListener: OpenListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_view_search_result, parent, false
        )
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            openListener?.onItemClickedListener(items[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = items[position]

        holder.bind(movie)
    }

    fun updateItems(list: List<Movie>) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            val imageUrl = movie.poster?.let { getImageUrl(it) }

            ImageLoader.getInstance()?.load(imageUrl, WeakReference(itemView.movieImage))
            itemView.title.text = movie.title
        }
    }

    interface OpenListener {
        fun onItemClickedListener(movie: Movie)
    }
}