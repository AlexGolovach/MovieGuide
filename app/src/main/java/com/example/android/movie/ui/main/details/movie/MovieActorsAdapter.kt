package com.example.android.movie.ui.main.details.movie

import ImageLoader
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.database.model.ActorSquad
import com.example.android.movie.R
import kotlinx.android.synthetic.main.item_view_actor.view.*
import java.lang.ref.WeakReference

class MovieActorsAdapter(private var items: List<ActorSquad> = emptyList()) :
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
            listener?.onItemClicked(items[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val actor = items[position]

        holder.bind(actor)
    }

    fun setItems(list: List<ActorSquad>) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(actor: ActorSquad) {

            ImageLoader.getInstance()?.load(actor.image, WeakReference(itemView.actorImage))
            itemView.actorName.text = actor.actorName
            itemView.actorCharacter.text = actor.character
        }
    }

    interface Listener {

        fun onItemClicked(actor: ActorSquad)
    }
}