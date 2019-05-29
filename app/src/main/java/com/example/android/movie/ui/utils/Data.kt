package com.example.android.movie.ui.utils

import android.os.Bundle

fun getBundleWithId(key: String, id: Int?): Bundle {
    val bundle = Bundle()
    id?.let { bundle.putInt(key, it) }

    return bundle
}

fun convertTime(time: Int?): String? {

    val hours = time?.div(60)
    val minutes = hours?.times(60)?.let { time.minus(it) }

    return if (hours == 0) {
        "$minutes min"
    } else {
        "$hours hr $minutes min"
    }
}