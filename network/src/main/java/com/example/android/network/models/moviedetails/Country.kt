package com.example.android.network.models.moviedetails

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("iso_3166_1")
    val isoCountry: String?,

    @SerializedName("name")
    val name: String?
)