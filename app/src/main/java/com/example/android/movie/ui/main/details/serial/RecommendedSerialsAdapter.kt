package com.example.android.movie.ui.main.details.serial

import ImageLoader
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.database.model.Movie
import com.example.android.movie.R
import kotlinx.android.synthetic.main.item_view_recommended_serial.view.*
import java.lang.ref.WeakReference

class RecommendedSerialsAdapter(private var items: List<Movie> = emptyList()) :
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
            listener?.onItemClicked(items[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val serial = items[position]

        holder.bind(serial)
    }

    fun setItems(list: List<Movie>) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(serial: Movie) {

            ImageLoader.getInstance()?.load(serial.image, WeakReference(itemView.posterShowImage))
            itemView.showTitleText.text = serial.title
            itemView.showRatingText.text = serial.rating.toString()
        }
    }

    interface Listener {

        fun onItemClicked(serial: Movie)
    }
}