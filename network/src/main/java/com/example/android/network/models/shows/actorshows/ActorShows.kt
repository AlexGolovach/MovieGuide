package com.example.android.network.models.shows.actorshows

import com.google.gson.annotations.SerializedName

data class ActorShows(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int?
)