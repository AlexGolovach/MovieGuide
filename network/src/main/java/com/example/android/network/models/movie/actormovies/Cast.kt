package com.example.android.network.models.movie.actormovies

import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("character")
    val character: String?,

    @SerializedName("creditId")
    val creditId: String?,

    @SerializedName("poster_path")
    val poster: String?,

    @SerializedName("id")
    val id: Int,

    @SerializedName("video")
    val video: Boolean,

    @SerializedName("vote_count")
    val voteCount: Int?,

    @SerializedName("adult")
    val adult: Boolean,

    @SerializedName("backdrop_path")
    val image: String?,

    @SerializedName("genre_ids")
    val genreIds: List<Int>,

    @SerializedName("original_language")
    val originalLanguage: String?,

    @SerializedName("original_title")
    val originalTitle: String?,

    @SerializedName("popularity")
    val popularity: Double?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("vote_average")
    val rating: Double?,

    @SerializedName("overview")
    val description: String?,

    @SerializedName("release_date")
    val releaseDate: String?
)