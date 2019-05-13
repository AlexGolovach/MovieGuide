package com.example.android.movie.ui.main.moviedetails

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.imageloader.Callback
import com.example.android.movie.R
import com.example.android.network.getImageUrl
import com.example.android.network.models.movie.MovieActorSquad
import kotlinx.android.synthetic.main.item_view_actor.view.*

class MovieActorsAdapter(private var items: List<MovieActorSquad> = listOf()) :
    RecyclerView.Adapter<MovieActorsAdapter.ViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MovieActorsAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_actor, parent, false)
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            listener?.onItemClicked(items[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: MovieActorsAdapter.ViewHolder, position: Int) {
        val actor = items[position]

        holder.bind(actor)
    }

    fun setItems(list: List<MovieActorSquad>) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(actor: MovieActorSquad) {
            val imageUrl = getImageUrl(actor.image)

            ImageLoader.getInstance()?.load(imageUrl, object : Callback {
                override fun onSuccess(url: String, bitmap: Bitmap) {
                    if (imageUrl == url) {
                        itemView.actor_image.background = null
                        itemView.actor_image.setImageBitmap(bitmap)
                    }
                }

                override fun onError(url: String, throwable: Throwable) {
                    if (imageUrl == url) {
                        itemView.actor_image.setImageResource(R.drawable.actor_placeholder)
                    }
                }
            })

            itemView.actor_name.text = actor.name
            itemView.actor_character.text = actor.character
        }
    }

    interface Listener {

        fun onItemClicked(actor: MovieActorSquad)
    }
}