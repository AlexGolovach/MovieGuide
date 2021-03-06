package com.example.android.network.models

import com.google.gson.annotations.SerializedName

data class VideoList(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("results")
    val results: List<Video>
) {
    data class Video(
        @SerializedName("id")
        val id: String?,

        @SerializedName("iso_639_1")
        val isoLanguage: String?,

        @SerializedName("iso_3166_1")
        val isoCountry: String?,

        @SerializedName("key")
        val key: String?,

        @SerializedName("name")
        val name: String?,

        @SerializedName("site")
        val site: String?,

        @SerializedName("size")
        val size: Int?,

        @SerializedName("type")
        val type: String?
    )
}