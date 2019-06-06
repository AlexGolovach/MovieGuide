package com.example.android.movie.ui.main.moviedetails.actor

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.imageloader.Callback
import com.example.android.movie.R
import com.example.android.network.Converter.Companion.getImageUrl
import com.example.android.network.models.actor.ActorImages
import com.example.android.network.models.actor.Image
import kotlinx.android.synthetic.main.item_view_actor_image.view.*

class ActorImageAdapter(private var items: ActorImages = ActorImages(emptyList(),0)) :
    RecyclerView.Adapter<ActorImageAdapter.ViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_actor_image, parent, false)
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            listener?.onItemClicked(items.profiles[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = items.profiles[position]

        holder.bind(image)
    }

    fun setItems(list: ActorImages) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.profiles.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(actorImage: Image) {
            val imageUrl = actorImage.image?.let { getImageUrl(it) }

            imageUrl?.let {
                ImageLoader.getInstance()?.load(it, object : Callback{
                    override fun onSuccess(url: String, bitmap: Bitmap) {
                        if (imageUrl == url){
                            itemView.actorImage.background = null
                            itemView.actorImage.setImageBitmap(bitmap)
                        }
                    }

                    override fun onError(url: String, throwable: Throwable) {
                        if (imageUrl == url){
                            itemView.actorImage.setImageResource(R.drawable.image_placeholder)
                        }
                    }
                })
            }
        }
    }

    interface Listener {

        fun onItemClicked(actorImage: Image)
    }
}