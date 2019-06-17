package com.example.android.network.models.serialsquad

import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("character")
    val character: String?,

    @SerializedName("credit_id")
    val creditId: String?,

    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("gender")
    val gender: Int?,

    @SerializedName("profile_path")
    val profileImage: String?,

    @SerializedName("order")
    val order: Int?
)