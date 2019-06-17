package com.example.android.movie.ui.main.topserials

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.movie.R
import com.example.android.network.Converter.getImageUrl
import com.example.android.network.models.serial.Serial
import com.example.android.network.models.serial.SerialsList
import kotlinx.android.synthetic.main.item_view_search_result.view.*
import java.lang.ref.WeakReference

class SearchResultSerialsAdapter(private var items: List<Serial> = listOf()) :
    RecyclerView.Adapter<SearchResultSerialsAdapter.ViewHolder>() {

    var openListener: OpenListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_view_search_result, parent, false
        )
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            openListener?.onItemClickedListener(items[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val serial = items[position]

        holder.bind(serial)
    }

    fun updateItems(list: List<Serial>) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(serial: Serial) {
            val imageUrl = serial.poster?.let { getImageUrl(it) }

            ImageLoader.getInstance()?.load(imageUrl, WeakReference(itemView.movieImage))
            itemView.title.text = serial.title
        }
    }

    interface OpenListener {
        fun onItemClickedListener(serial: Serial)
    }
}