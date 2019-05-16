package com.example.android.network.models.movie.actormovies

import com.google.gson.annotations.SerializedName

data class ActorMovies(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Long
)