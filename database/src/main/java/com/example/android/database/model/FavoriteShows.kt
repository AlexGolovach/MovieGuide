package com.example.android.database.model

data class FavoriteShows (
    val documentId: String = "",
    val showId: Int = 0,
    val userId: String = "",
    val show: String = "",
    val poster: String = "",
    val rating: Double = 0.0
)