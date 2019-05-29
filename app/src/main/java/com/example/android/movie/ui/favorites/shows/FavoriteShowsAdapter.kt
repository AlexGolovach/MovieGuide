package com.example.android.movie.ui.favorites.shows

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.database.model.FavoriteShows
import com.example.android.movie.R
import com.example.android.network.Converter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view_favorite_show.view.*

class FavoriteShowsAdapter(private var items: ArrayList<FavoriteShows> = ArrayList()) :
    RecyclerView.Adapter<FavoriteShowsAdapter.ViewHolder>() {

    var openListener: OpenListener? = null
    var deleteListener: DeleteListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_favorite_show, parent, false)
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
        val show = items[position]

        Picasso.get()
            .load(Converter.getImageUrl(show.poster))
            .placeholder(R.drawable.image_placeholder)
            .error(R.drawable.image_placeholder)
            .into(holder.itemView.show_poster_image)

        holder.itemView.show_title_text.text = show.show
        holder.itemView.show_rating_text.text = show.rating.toString()
    }

    fun setItems(list: ArrayList<FavoriteShows>) {
        items.clear()
        items.addAll(list)

        notifyDataSetChanged()
    }

    fun deleteItems(show: FavoriteShows) {
        val iterator = items.iterator()

        while (iterator.hasNext()) {
            val item = iterator.next()

            if (show == item) {
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
        fun onOpenClikedListener(show: FavoriteShows)
    }

    interface DeleteListener {
        fun onDeleteClickedListener(show: FavoriteShows)
    }
}