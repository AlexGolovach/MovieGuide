package com.example.android.network.models.serial

import com.google.gson.annotations.SerializedName

data class Serial (
    @SerializedName("original_name")
    val originalName: String?,

    @SerializedName("genre_ids")
    val genreIds: List<Int>,

    @SerializedName("name")
    val title: String?,

    @SerializedName("popularity")
    val popularity: Double?,

    @SerializedName("origin_country")
    val originCountry: List<String>,

    @SerializedName("vote_count")
    val voteCount: Int?,

    @SerializedName("first_air_date")
    val releaseDate: String?,

    @SerializedName("backdrop_path")
    val image: String?,

    @SerializedName("original_language")
    val language: String?,

    @SerializedName("id")
    val id: Int?,

    @SerializedName("vote_average")
    val rating: Double?,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("poster_path")
    val poster: String?
)