package com.example.android.network.models.actor

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("iso_639_1")
    val isoLanguage: String?,

    @SerializedName("width")
    val width: Int?,

    @SerializedName("height")
    val height: Int?,

    @SerializedName("vote_count")
    val voteCount: Int?,

    @SerializedName("vote_average")
    val voteAverage: Double?,

    @SerializedName("file_path")
    val image: String?,

    @SerializedName("aspect_ratio")
    val aspectRatio: Double?
)