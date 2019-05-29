package com.example.android.movie.ui.main.information.details.show

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.movie.R
import com.example.android.network.Converter.Companion.getImageUrl
import com.example.android.network.models.showsquad.Cast
import com.example.android.network.models.showsquad.ActorShowSquad
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view_show_actor_squad.view.*

class ShowActorsAdapter(
    private var items: ActorShowSquad = ActorShowSquad(
        0, emptyList(),
        emptyList()
    )
) :
    RecyclerView.Adapter<ShowActorsAdapter.ViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_show_actor_squad, parent, false)
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

    fun setItems(list: ActorShowSquad) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.cast.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(actor: Cast) {
            val imageUrl = actor.profile_image?.let { getImageUrl(it) }

            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.actor_placeholder)
                .error(R.drawable.actor_placeholder)
                .into(itemView.actor_image)

            itemView.actor_name.text = actor.name
            itemView.actor_character.text = actor.character
        }
    }

    interface Listener {

        fun onItemClicked(actor: Cast)
    }
}