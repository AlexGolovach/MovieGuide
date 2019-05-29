package com.example.android.database.model

data class FavoriteMovies(
    val documentId: String = "",
    val movieId: Int = 0,
    val userId: String = "",
    val movie: String = "",
    val poster: String = "",
    val rating: Double = 0.0
)
