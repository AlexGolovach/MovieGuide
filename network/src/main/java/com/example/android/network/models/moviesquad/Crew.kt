package com.example.android.network.models.moviesquad

import com.google.gson.annotations.SerializedName

data class Crew(
    @SerializedName("credit_id")
    val credit_id: String?,
    @SerializedName("department")
    val department: String?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("id")
    val id: Long?,
    @SerializedName("job")
    val job: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("profile_path")
    val image: String?
)