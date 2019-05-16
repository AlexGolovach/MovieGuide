package com.example.android.network.models.moviedetails

import com.google.gson.annotations.SerializedName

data class Language(
    @SerializedName("iso_639_1")
    val iso_639_1: String?,
    @SerializedName("name")
    val name: String?
)