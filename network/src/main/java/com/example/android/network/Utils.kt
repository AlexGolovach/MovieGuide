package com.example.android.network

fun getImageUrl(image: String): String {
    val list = listOf(Constants.FIRST_PART_IMAGE_URL, image)
    return list.filter { !it.isEmpty() }.joinToString("")
}