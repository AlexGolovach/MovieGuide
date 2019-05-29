package com.example.android.network.models.shows.actorshows

import com.google.gson.annotations.SerializedName

data class Cast (
    @SerializedName("name")
    val name: String?,
    @SerializedName("character")
    val character: String?,
    @SerializedName("credit_id")
    val credit_id: String?,
    @SerializedName("poster_path")
    val poster: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("vote_count")
    val vote_count: Int?,
    @SerializedName("backdrop_path")
    val image: String?,
    @SerializedName("genre_ids")
    val genre_ids: List<Int>,
    @SerializedName("original_language")
    val original_language: String?,
    @SerializedName("original_name")
    val original_name: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("vote_average")
    val rating: Double?,
    @SerializedName("overview")
    val description: String?,
    @SerializedName("first_air_date")
    val releaseDate: String?,
    @SerializedName("episode_count")
    val episode_count: Int?,
    @SerializedName("origin_country")
    val origin_country: List<String>
)