package com.example.android.network.models.movie

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("vote_count")
    val vote_count: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("vote_average")
    val rating: Double?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val poster: String?,
    @SerializedName("original_language")
    val original_language: String?,
    @SerializedName("original_title")
    val original_title: String?,
    @SerializedName("genre_ids")
    val genre_ids: List<Int>,
    @SerializedName("backdrop_path")
    val image: String?,
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("overview")
    val description: String?,
    @SerializedName("release_date")
    val release_date: String?
)