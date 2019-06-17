package com.example.android.movie.ui.utils

import android.os.Bundle

fun getBundleWithId(key: String, id: Int?): Bundle {
    val bundle = Bundle()
    id?.let { bundle.putInt(key, it) }

    return bundle
}

fun getBundleWithVideo(key: String, videoId: String?): Bundle {
    val bundle = Bundle()
    videoId?.let { bundle.putString(key, it) }

    return bundle
}

fun convertTime(time: Int?): String? {

    val hours = time?.div(60)
    val minutes = hours?.times(60)?.let { time.minus(it) }

    return "$hours hr $minutes min"
}