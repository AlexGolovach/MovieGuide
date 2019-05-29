package com.example.android.network.models.showsquad

import com.google.gson.annotations.SerializedName

data class ActorShowSquad(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>
)