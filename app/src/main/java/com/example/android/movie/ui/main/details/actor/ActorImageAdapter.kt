package com.example.android.movie.ui.main.details.actor

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.movie.R
import kotlinx.android.synthetic.main.item_view_actor_image.view.*
import java.lang.ref.WeakReference

class ActorImageAdapter(private var items: List<String> = emptyList()) :
    RecyclerView.Adapter<ActorImageAdapter.ViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_actor_image, parent, false)
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            listener?.onItemClicked(items[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = items[position]

        holder.bind(image)
    }

    fun setItems(list: List<String>) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(actorImage: String) {

            ImageLoader.getInstance()?.load(actorImage, WeakReference(itemView.actorImage))
        }
    }

    interface Listener {

        fun onItemClicked(actorImage: String)
    }
}