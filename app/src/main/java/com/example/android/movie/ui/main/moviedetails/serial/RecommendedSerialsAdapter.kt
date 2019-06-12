package com.example.android.movie.ui.main.moviedetails.serial

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.imageloader.Callback
import com.example.android.movie.R
import com.example.android.network.Converter.Companion.getImageUrl
import com.example.android.network.models.recommendedserials.RecommendSerials
import com.example.android.network.models.recommendedserials.RecommendSerialsList
import kotlinx.android.synthetic.main.item_view_recommended_serial.view.*

class RecommendedSerialsAdapter(
    private var items: RecommendSerialsList = RecommendSerialsList(
        0,
        0,
        0,
        emptyList()
    )
) :
    RecyclerView.Adapter<RecommendedSerialsAdapter.ViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        p1: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_recommended_serial, parent, false)
        val holder =
            ViewHolder(
                view
            )

        holder.itemView.setOnClickListener {
            listener?.onItemClicked(items.results[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val serial = items.results[position]

        holder.bind(serial)
    }

    fun setItems(list: RecommendSerialsList) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.results.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(show: RecommendSerials) {
            val imageUrl = show.image?.let { getImageUrl(it) }

            imageUrl?.let {
                ImageLoader.getInstance()?.load(it, object : Callback {
                    override fun onSuccess(url: String, bitmap: Bitmap) {
                        if (imageUrl == url) {
                            itemView.poster_show_image.background = null
                            itemView.poster_show_image.setImageBitmap(bitmap)
                        }
                    }

                    override fun onError(url: String, throwable: Throwable) {
                        if (imageUrl == url) {
                            itemView.poster_show_image.setImageResource(R.drawable.actor_placeholder)
                        }
                    }
                })
            }

            itemView.show_title_text.text = show.title
            itemView.show_rating_text.text = show.rating.toString()
        }
    }

    interface Listener {

        fun onItemClicked(serial: RecommendSerials)
    }
}