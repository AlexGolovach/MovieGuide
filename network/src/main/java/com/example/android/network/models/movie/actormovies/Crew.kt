package com.example.android.network.models.movie.actormovies

import com.google.gson.annotations.SerializedName

data class Crew(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("department")
    val department: String?,
    @SerializedName("original_language")
    val original_language: String?,
    @SerializedName("original_title")
    val original_title: String?,
    @SerializedName("job")
    val job: String?,
    @SerializedName("overview")
    val description: String?,
    @SerializedName("vote_count")
    val vote_count: Int?,
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
    val genre_ids: List<Int>,
    @SerializedName("vote_average")
    val rating: Double?,
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("credit_id")
    val credit_id: String?
)