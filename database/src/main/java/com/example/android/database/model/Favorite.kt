package com.example.android.database.model

data class Favorite(
    val favoriteId: Int,
    val userId: Int,
    val movieOrSerialId: Int,
    val title: String,
    val image: String,
    val type: String
)