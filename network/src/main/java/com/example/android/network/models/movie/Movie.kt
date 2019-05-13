package com.example.android.network.models.movie

data class Movie(
    val id: Long,
    val title: String,
    val image: String,
    val description: String,
    val rating: Double
)