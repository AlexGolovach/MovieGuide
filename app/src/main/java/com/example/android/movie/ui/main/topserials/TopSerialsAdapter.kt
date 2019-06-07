package com.example.android.movie.ui.main.topserials

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.imageloader.Callback
import com.example.android.movie.R
import com.example.android.network.Converter.Companion.getImageUrl
import com.example.android.network.models.serial.Serial
import com.example.android.network.models.serial.SerialsList
import kotlinx.android.synthetic.main.item_view_top_serials.view.*

class TopSerialsAdapter(private var items: SerialsList = SerialsList(0, 0, 0, emptyList())) :
    RecyclerView.Adapter<TopSerialsAdapter.ViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_top_serials, parent, false)
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            listener?.onItemClicked(items.results[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val serial = items.results[position]

        holder.bind(serial)
    }

    fun setItems(list: SerialsList) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.results.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(serial: Serial) {
            val imageUrl = serial.image?.let { getImageUrl(it) }

            imageUrl?.let {
                ImageLoader.getInstance()?.load(it, object : Callback {
                    override fun onSuccess(url: String, bitmap: Bitmap) {
                        if (imageUrl == url) {
                            itemView.serialPosterImage.background = null
                            itemView.serialPosterImage.setImageBitmap(bitmap)
                        }
                    }

                    override fun onError(url: String, throwable: Throwable) {
                        if (imageUrl == url) {
                            itemView.serialPosterImage.setImageResource(R.drawable.image_placeholder)
                        }
                    }
                })
            }

            itemView.serialTitleText.text = serial.title
            itemView.serialRatingText.text = serial.rating.toString()
        }
    }

    interface Listener {

        fun onItemClicked(serial: Serial)
    }
}