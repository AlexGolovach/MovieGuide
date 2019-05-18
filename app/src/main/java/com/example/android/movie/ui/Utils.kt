package com.example.android.movie.ui

import android.os.Bundle

fun getBundleWithId(key: String, id: Int?): Bundle {

    val bundle = Bundle()
    id?.let { bundle.putInt(key, it) }

    return bundle
}