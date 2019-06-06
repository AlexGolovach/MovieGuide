package com.example.android.network.models.moviesquad

import com.google.gson.annotations.SerializedName

data class MovieActorSquad(
    @SerializedName("id")
    val id: Long?,

    @SerializedName("cast")
    val cast: List<Cast>,

    @SerializedName("crew")
    val crew: List<Crew>
)