package com.example.android.movie.ui.main.information.details.show

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.movie.R
import com.example.android.network.Converter.Companion.getImageUrl
import com.example.android.network.models.recommendedshows.RecommendShows
import com.example.android.network.models.recommendedshows.RecommendShowsList
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view_recommended_show.view.*

class RecommendedShowsAdapter(private var items: RecommendShowsList = RecommendShowsList(0, 0, 0, emptyList())) :
    RecyclerView.Adapter<RecommendedShowsAdapter.ViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        p1: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_recommended_show, parent, false)
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
        val show = items.results[position]

        holder.bind(show)
    }

    fun setItems(list: RecommendShowsList) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.results.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(show: RecommendShows) {
            val imageUrl = show.image?.let { getImageUrl(it) }

            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(itemView.poster_show_image)

            itemView.show_title_text.text = show.title
            itemView.show_rating_text.text = show.rating.toString()
        }
    }

    interface Listener {

        fun onItemClicked(show: RecommendShows)
    }
}