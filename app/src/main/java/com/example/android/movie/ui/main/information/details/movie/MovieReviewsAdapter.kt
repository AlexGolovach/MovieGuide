package com.example.android.movie.ui.main.information.details.movie

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.database.model.Reviews
import com.example.android.movie.R
import com.example.android.movie.ui.utils.AccountOperation
import kotlinx.android.synthetic.main.item_view_movie_review.view.*

class MovieReviewsAdapter(private var items: ArrayList<Reviews> = ArrayList()) :
    RecyclerView.Adapter<MovieReviewsAdapter.ViewHolder>() {

    var deleteListener: DeleteListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_movie_review, parent, false)
        val holder =
            ViewHolder(
                view
            )

        holder.itemView.icon_delete.setOnClickListener {
            deleteListener?.onDeleteClickedListener(items[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = items[position]

        holder.bind(review)
    }

    fun updateItems(list: ArrayList<Reviews>) {
        items = list

        notifyDataSetChanged()
    }

    fun deleteItems(review: Reviews) {
        val iterator = items.iterator()

        while (iterator.hasNext()) {
            val item = iterator.next()

            if (review == item) {
                iterator.remove()
            }
        }

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(review: Reviews) {

            itemView.apply {
                if (review.userName == AccountOperation.getAccount().login) {
                    icon_delete.visibility = View.VISIBLE
                } else {
                    icon_delete.visibility = View.GONE
                }

                username_text.text = review.userName
                review_text.text = review.review
            }
        }
    }

    interface DeleteListener {
        fun onDeleteClickedListener(reviews: Reviews)
    }
}