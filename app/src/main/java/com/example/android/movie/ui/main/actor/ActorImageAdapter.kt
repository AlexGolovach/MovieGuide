package com.example.android.movie.ui.main.actor

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.imageloader.Callback
import com.example.android.movie.R
import com.example.android.network.getImageUrl
import kotlinx.android.synthetic.main.item_view_actor_image.view.*

class ActorImageAdapter(private var items: List<String> = listOf()) :
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
        fun bind(image: String) {
            val imageUrl = getImageUrl(image)

            ImageLoader.getInstance()?.load(imageUrl, object : Callback{
                override fun onSuccess(url: String, bitmap: Bitmap) {
                    if (imageUrl == url){
                        itemView.actor_image.background = null
                        itemView.actor_image.setImageBitmap(bitmap)
                    }
                }

                override fun onError(url: String, throwable: Throwable) {
                    if (imageUrl == url){
                        itemView.actor_image.setImageResource(R.drawable.image_placeholder)
                    }
                }
            })
        }
    }

    interface Listener {

        fun onItemClicked(image: String)
    }
}