package com.example.android.network.models.actor

import com.google.gson.annotations.SerializedName

data class ActorImages(
    @SerializedName("iso_639_1")
    val iso_639_1: String?,
    @SerializedName("width")
    val width: Int?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("vote_count")
    val vote_count: Int?,
    @SerializedName("vote_average")
    val vote_average: Double?,
    @SerializedName("file_path")
    val image: String?,
    @SerializedName("aspect_ratio")
    val aspect_ratio: Double?
)