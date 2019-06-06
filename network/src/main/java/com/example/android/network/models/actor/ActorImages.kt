package com.example.android.network.models.actor

import com.google.gson.annotations.SerializedName

data class ActorImages(
    @SerializedName("profiles")
    val profiles: List<Image>,

    @SerializedName("id")
    val id: Int?
)