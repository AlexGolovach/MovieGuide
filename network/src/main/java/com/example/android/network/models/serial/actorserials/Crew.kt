package com.example.android.network.models.serial.actorserials

import com.google.gson.annotations.SerializedName

data class Crew(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("department")
    val department: String?,

    @SerializedName("original_language")
    val originalLanguage: String?,

    @SerializedName("original_country")
    val originalCountry: List<String>,

    @SerializedName("job")
    val job: String?,

    @SerializedName("overview")
    val description: String?,

    @SerializedName("vote_count")
    val voteCount: Int?,

    @SerializedName("poster_path")
    val poster: String?,

    @SerializedName("backdrop_path")
    val image: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("popularity")
    val popularity: Double?,

    @SerializedName("genre_ids")
    val genreIds: List<Int>,

    @SerializedName("vote_average")
    val rating: Double?,

    @SerializedName("first_air_date")
    val firstAirDate: String?,

    @SerializedName("credit_id")
    val creditId: String?,

    @SerializedName("original_name")
    val originalName: String?,

    @SerializedName("episode_count")
    val episodeCount: Int?
)