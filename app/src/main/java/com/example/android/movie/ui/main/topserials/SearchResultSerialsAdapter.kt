package com.example.android.movie.ui.main.topserials

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.movie.R
import com.example.android.network.models.serial.Serial
import com.example.android.network.models.serial.SerialsList
import kotlinx.android.synthetic.main.item_view_search_result.view.*

class SearchResultSerialsAdapter(
    private var items: SerialsList = SerialsList(
        0,
        0,
        0,
        emptyList()
    )
) :
    RecyclerView.Adapter<SearchResultSerialsAdapter.ViewHolder>() {

    var openListener: OpenListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_view_search_result, parent, false
        )
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            openListener?.onItemClickedListener(items.results[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val serial = items.results[position]

        holder.itemView.title.text = serial.title
    }

    fun updateItems(list: SerialsList) {
        items = SerialsList(0, 0, 0, emptyList())
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.results.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OpenListener {
        fun onItemClickedListener(serial: Serial)
    }
}