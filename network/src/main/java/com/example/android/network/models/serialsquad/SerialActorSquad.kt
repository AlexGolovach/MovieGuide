package com.example.android.network.models.serialsquad

import com.google.gson.annotations.SerializedName

data class SerialActorSquad(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>
)