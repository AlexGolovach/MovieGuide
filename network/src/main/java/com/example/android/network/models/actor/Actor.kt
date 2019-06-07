package com.example.android.network.models.actor

import com.google.gson.annotations.SerializedName

data class Actor(
    @SerializedName("birthday")
    val birthday: String?,

    @SerializedName("known_for_department")
    val knownForDepartment: String?,

    @SerializedName("deathday")
    val deathDay: String?,

    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("also_known_as")
    val alsoKnownAs: List<String>,

    @SerializedName("gender")
    val gender: Int?,

    @SerializedName("biography")
    val biography: String?,

    @SerializedName("popularity")
    val popularity: Double?,

    @SerializedName("place_of_birth")
    val placeOfBirth: String?,

    @SerializedName("profile_path")
    val image: String?,

    @SerializedName("adult")
    val adult: Boolean,

    @SerializedName("imdb_id")
    val imdbId: String?,

    @SerializedName("homepage")
    val homepage: String?
)