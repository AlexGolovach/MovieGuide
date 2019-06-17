package com.example.android.network.models.serial.actorserials

import com.google.gson.annotations.SerializedName

data class ActorSerials(
    @SerializedName("cast")
    val cast: List<Cast>,

    @SerializedName("crew")
    val crew: List<Crew>,

    @SerializedName("id")
    val id: Int?
)