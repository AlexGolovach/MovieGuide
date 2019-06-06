package com.example.android.movie.ui.main.moviedetails.details

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.imageloader.Callback
import com.example.android.movie.R
import com.example.android.network.Converter.Companion.getImageUrl
import com.example.android.network.models.moviesquad.Cast
import com.example.android.network.models.moviesquad.MovieActorSquad
import kotlinx.android.synthetic.main.item_view_actor.view.*

class MovieActorsAdapter(
    private var items: MovieActorSquad = MovieActorSquad(
        0, emptyList(),
        emptyList()
    )
) :
    RecyclerView.Adapter<MovieActorsAdapter.ViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_actor, parent, false)
        val holder =
            ViewHolder(
                view
            )

        holder.itemView.setOnClickListener {
            listener?.onItemClicked(items.cast[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val actor = items.cast[position]

        holder.bind(actor)
    }

    fun setItems(list: MovieActorSquad) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.cast.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(actor: Cast) {
            val imageUrl = actor.image?.let { getImageUrl(it) }

            imageUrl?.let {
                ImageLoader.getInstance()?.load(it, object : Callback {
                    override fun onSuccess(url: String, bitmap: Bitmap) {
                        if (imageUrl == url) {
                            itemView.actorImage.background = null
                            itemView.actorImage.setImageBitmap(bitmap)
                        }
                    }

                    override fun onError(url: String, throwable: Throwable) {
                        if (imageUrl == url) {
                            itemView.actorImage.setImageResource(R.drawable.actor_placeholder)
                        }
                    }
                })
            }

            itemView.actorName.text = actor.name
            itemView.actorCharacter.text = actor.character
        }
    }

    interface Listener {

        fun onItemClicked(actor: Cast)
    }
}