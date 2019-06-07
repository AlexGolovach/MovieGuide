package com.example.android.network.models.serial.actorserials

import com.google.gson.annotations.SerializedName

data class Crew(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("department")
    val department: String?,
    @SerializedName("original_language")
    val original_language: String?,
    @SerializedName("original_country")
    val original_country: List<String>,
    @SerializedName("job")
    val job: String?,
    @SerializedName("overview")
    val description: String?,
    @SerializedName("vote_count")
    val vote_count: Int?,
    @SerializedName("poster_path")
    val poster: String?,
    @SerializedName("backdrop_path")
    val image: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("genre_ids")
    val genre_ids: List<Int>,
    @SerializedName("vote_average")
    val rating: Double?,
    @SerializedName("first_air_date")
    val first_air_date: String?,
    @SerializedName("credit_id")
    val credit_id: String?,
    @SerializedName("original_name")
    val original_name: String?,
    @SerializedName("episode_count")
    val episode_count: Int?
)