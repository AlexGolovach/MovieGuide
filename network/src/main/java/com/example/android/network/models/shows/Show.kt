package com.example.android.network.models.shows

import com.google.gson.annotations.SerializedName

data class Show (
    @SerializedName("original_name")
    val original_name: String?,
    @SerializedName("genre_ids")
    val genre_ids: List<Int>,
    @SerializedName("name")
    val title: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("origin_country")
    val origin_country: List<String>,
    @SerializedName("vote_count")
    val vote_count: Int?,
    @SerializedName("first_air_date")
    val release_date: String?,
    @SerializedName("backdrop_path")
    val image: String?,
    @SerializedName("original_language")
    val language: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("vote_average")
    val rating: Double?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val poster: String?
)





//      "vote_average": 8.4,
//      "vote_count": 3272,
//      "networks": [
//        {
//          "id": 174,
//          "logo": {
//            "path": "/pmvRmATOCaDykE6JrVoeYxlFHw3.png",
//            "aspect_ratio": 1.768
//          },
//          "name": "AMC",
//          "origin_country": "US"
//        }
//      ],
//      "popularity": 95.977