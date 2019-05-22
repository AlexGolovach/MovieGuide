package com.example.android.network.models.movievideo

import com.google.gson.annotations.SerializedName

data class MovieVideos(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("results")
    val results: List<Video>
)