package com.example.android.network.models.showsquad

import com.google.gson.annotations.SerializedName

data class Crew(
    @SerializedName("credit_id")
    val credit_id: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("profile_path")
    val profile_image: String?,
    @SerializedName("department")
    val department: String?,
    @SerializedName("job")
    val job: String?
)