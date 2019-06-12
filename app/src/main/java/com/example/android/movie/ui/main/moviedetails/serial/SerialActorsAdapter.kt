package com.example.android.movie.ui.main.moviedetails.serial

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.imageloader.Callback
import com.example.android.movie.R
import com.example.android.network.Converter.Companion.getImageUrl
import com.example.android.network.models.serialsquad.Cast
import com.example.android.network.models.serialsquad.SerialActorSquad
import kotlinx.android.synthetic.main.item_view_serial_actor_squad.view.*

class SerialActorsAdapter(
    private var items: SerialActorSquad = SerialActorSquad(
        0, emptyList(),
        emptyList()
    )
) :
    RecyclerView.Adapter<SerialActorsAdapter.ViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_serial_actor_squad, parent, false)
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

    fun setItems(list: SerialActorSquad) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.cast.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(actor: Cast) {
            val imageUrl = actor.profile_image?.let { getImageUrl(it) }

            imageUrl?.let {
                ImageLoader.getInstance()?.load(it, object : Callback {
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
            }

            itemView.actor_name.text = actor.name
            itemView.actor_character.text = actor.character
        }
    }

    interface Listener {

        fun onItemClicked(actor: Cast)
    }
}