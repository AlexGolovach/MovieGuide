package com.example.android.movie.ui.utils

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder<in T>(itemView: View): RecyclerView.ViewHolder(itemView){

    abstract fun bind(item: T)
}