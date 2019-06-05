package com.example.android.movie.ui.fullserialsfilms.fullserials

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.example.android.database.model.Serial
import com.example.android.movie.R
import kotlinx.android.synthetic.main.item_view_full_serials.view.*

class FullSerialsAdapter(private var items: List<Serial> = emptyList()) :
    RecyclerView.Adapter<FullSerialsAdapter.ViewHolder>(), Filterable {

    private var serialsSearchList: List<Serial>? = null
    var openListener: OpenListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_view_full_serials, parent, false
        )
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            serialsSearchList?.get(holder.adapterPosition)?.let { serial ->
                openListener?.onItemClickedListener(
                    serial
                )
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val serial = serialsSearchList?.get(position)

        holder.itemView.serial_title.text = serial?.serialTitle
    }

    fun setItems(list: List<Serial>) {
        items = list
        serialsSearchList = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return serialsSearchList!!.size
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    serialsSearchList = items
                } else {
                    val filteredList = ArrayList<Serial>()
                    for (row in items) {
                        if (row.serialTitle.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    serialsSearchList = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = serialsSearchList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
                serialsSearchList = filterResults?.values as ArrayList<Serial>
                notifyDataSetChanged()
            }

        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OpenListener {
        fun onItemClickedListener(serial: Serial)
    }
}