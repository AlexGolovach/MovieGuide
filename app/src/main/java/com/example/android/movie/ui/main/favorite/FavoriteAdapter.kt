package com.example.android.movie.ui.main.favorite

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.database.model.Favorite
import com.example.android.movie.R
import kotlinx.android.synthetic.main.item_view_favorite.view.*
import java.lang.ref.WeakReference

class FavoriteAdapter(private var items: ArrayList<Favorite> = ArrayList()) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    var clickListener: OnClickListener? = null
    var deleteListener: OnDeleteListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_favorite, parent, false)
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            clickListener?.onItemClicked(items[holder.adapterPosition])
        }

        holder.itemView.deleteFromFavorite.setOnClickListener {
            deleteListener?.onDeleteClicked(items[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favorite = items[position]

        holder.bind(favorite)
    }

    fun setItems(list: ArrayList<Favorite>) {
        items = list
        notifyDataSetChanged()
    }

    fun deleteItems(favorite: Favorite) {
        val iterator = items.iterator()

        while (iterator.hasNext()) {
            val item = iterator.next()

            if (favorite == item) {
                iterator.remove()
            }
        }

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(favorite: Favorite) {
            ImageLoader.getInstance()?.load(favorite.image, WeakReference(itemView.posterImage))
            itemView.titleText.text = favorite.title
        }
    }

    interface OnClickListener {

        fun onItemClicked(favorite: Favorite)
    }

    interface OnDeleteListener {

        fun onDeleteClicked(favorite: Favorite)
    }
}