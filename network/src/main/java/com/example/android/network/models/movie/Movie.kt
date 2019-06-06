package com.example.android.network.models.movie

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("vote_count")
    val voteCount: Int?,

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
    val originalLanguage: String?,

    @SerializedName("original_title")
    val originalTitle: String?,

    @SerializedName("genre_ids")
    val genreIds: List<Int>,

    @SerializedName("backdrop_path")
    val image: String?,

    @SerializedName("adult")
    val adult: Boolean,

    @SerializedName("overview")
    val description: String?,

    @SerializedName("release_date")
    val releaseDate: String?
)