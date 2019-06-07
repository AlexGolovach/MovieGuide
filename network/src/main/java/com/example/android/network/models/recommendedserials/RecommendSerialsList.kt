package com.example.android.network.models.recommendedserials

import com.google.gson.annotations.SerializedName

data class RecommendSerialsList (
    @SerializedName("page")
    val page: Int?,
    @SerializedName("total_results")
    val total_results: Int?,
    @SerializedName("total_pages")
    val total_pages: Int?,
    @SerializedName("results")
    val results: List<RecommendSerials>
)