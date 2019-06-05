package com.example.android.movie.ui.fullserialsfilms.fullfilms

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.example.android.database.model.Film
import com.example.android.movie.R
import kotlinx.android.synthetic.main.item_view_full_films.view.*

class FullFilmsAdapter(private var items: List<Film> = emptyList()) :
    RecyclerView.Adapter<FullFilmsAdapter.ViewHolder>(), Filterable {

    private var filmsSearchList: List<Film>? = null
    var openListener: OpenListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_view_full_films, parent, false
        )
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            filmsSearchList?.get(holder.adapterPosition)?.let { film ->
                openListener?.onItemClickedListener(
                    film
                )
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = filmsSearchList?.get(position)

        holder.itemView.film_title.text = movie?.filmTitle
    }

    fun setItems(list: List<Film>) {
        items = list
        filmsSearchList = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return filmsSearchList!!.size
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    filmsSearchList = items
                } else {
                    val filteredList = ArrayList<Film>()
                    for (row in items) {
                        if (row.filmTitle.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    filmsSearchList = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = filmsSearchList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
                filmsSearchList = filterResults?.values as ArrayList<Film>
                notifyDataSetChanged()
            }

        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OpenListener {
        fun onItemClickedListener(movie: Film)
    }
}