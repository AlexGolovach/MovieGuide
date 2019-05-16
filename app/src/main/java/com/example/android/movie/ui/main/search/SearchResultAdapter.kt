package com.example.android.movie.ui.main.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.movie.R
import com.example.android.network.models.movie.Movie
import com.example.android.network.models.movie.MovieList
import kotlinx.android.synthetic.main.item_view_search_result.view.*

class SearchResultAdapter(private var items: MovieList = MovieList(0,0,0, emptyList())) :
    RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_view_search_result, parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = items.results[position]

        holder.itemView.movie_title.text = movie.title
    }

    fun updateItems(list: MovieList){
        items = MovieList(0,0,0, emptyList())
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.results.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}