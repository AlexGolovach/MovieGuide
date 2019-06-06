package com.example.android.network.models.movie.actormovies

import com.google.gson.annotations.SerializedName

data class Crew(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("department")
    val department: String?,

    @SerializedName("original_language")
    val originalLanguage: String?,

    @SerializedName("original_title")
    val originalTitle: String?,

    @SerializedName("job")
    val job: String?,

    @SerializedName("overview")
    val description: String?,

    @SerializedName("vote_count")
    val voteCount: Int?,

    @SerializedName("video")
    val video: Boolean,

    @SerializedName("poster_path")
    val poster: String?,

    @SerializedName("backdrop_path")
    val image: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("popularity")
    val popularity: Double?,

    @SerializedName("genre_ids")
    val genreIds: List<Int>,

    @SerializedName("vote_average")
    val rating: Double?,

    @SerializedName("adult")
    val adult: Boolean,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("creditId")
    val creditId: String?
)