package com.example.android.network.models.movie

import com.example.android.network.models.Genres

data class MovieDetails(
    val movie: Movie,
    val genres: Genres,
    val budget: Long,
    val homepage: String,
    val releaseDate: String
)