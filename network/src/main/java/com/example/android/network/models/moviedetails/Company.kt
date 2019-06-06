package com.example.android.network.models.moviedetails

import com.google.gson.annotations.SerializedName

data class Company(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("logo_path")
    val image: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("origin_country")
    val country: String?
)