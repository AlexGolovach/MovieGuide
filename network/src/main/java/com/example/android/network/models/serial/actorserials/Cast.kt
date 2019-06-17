package com.example.android.network.models.serial.actorserials

import com.google.gson.annotations.SerializedName

data class Cast (
    @SerializedName("name")
    val name: String?,

    @SerializedName("character")
    val character: String?,

    @SerializedName("credit_id")
    val creditId: String?,

    @SerializedName("poster_path")
    val poster: String?,

    @SerializedName("id")
    val id: Int?,

    @SerializedName("vote_count")
    val voteCount: Int?,

    @SerializedName("backdrop_path")
    val image: String?,

    @SerializedName("genre_ids")
    val genreIds: List<Int>,

    @SerializedName("original_language")
    val originalLanguage: String?,

    @SerializedName("original_name")
    val originalName: String?,

    @SerializedName("popularity")
    val popularity: Double?,

    @SerializedName("vote_average")
    val rating: Double?,

    @SerializedName("overview")
    val description: String?,

    @SerializedName("first_air_date")
    val releaseDate: String?,

    @SerializedName("episode_count")
    val episodeCount: Int?,

    @SerializedName("origin_country")
    val originCountry: List<String>
)