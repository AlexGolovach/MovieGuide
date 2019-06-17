package com.example.android.movie.ui.main.topserials

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.movie.R
import com.example.android.network.Converter.getImageUrl
import com.example.android.network.models.serial.Serial
import com.example.android.network.models.serial.SerialsList
import kotlinx.android.synthetic.main.item_view_top_serials.view.*
import java.lang.ref.WeakReference

class TopSerialsAdapter(private var items: ArrayList<Serial> = ArrayList()) :
    RecyclerView.Adapter<TopSerialsAdapter.ViewHolder>() {

    var listener: Listener? = null
    var loadListener: LoadItems? = null

    private var page: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_top_serials, parent, false)
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            listener?.onItemClicked(items[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val serial = items[position]

        if (position == itemCount - 1) {
            page?.let { loadListener?.onLoadItems(it.plus(1)) }
        }

        holder.bind(serial)
    }

    fun setItems(list: SerialsList) {
        page = list.page

        for (i in 0 until list.results.size) {
            items.add(list.results[i])
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(serial: Serial) {
            val imageUrl = serial.image?.let { getImageUrl(it) }

            ImageLoader.getInstance()?.load(imageUrl, WeakReference(itemView.serialPosterImage))
            itemView.serialTitleText.text = serial.title
            itemView.serialRatingText.text = serial.rating.toString()
        }
    }

    interface Listener {

        fun onItemClicked(serial: Serial)
    }

    interface LoadItems {

        fun onLoadItems(page: Int)
    }
}