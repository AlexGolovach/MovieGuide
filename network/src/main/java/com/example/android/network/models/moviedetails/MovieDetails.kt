package com.example.android.network.models.moviedetails

import com.example.android.network.models.Genres
import com.google.gson.annotations.SerializedName

data class MovieDetails(
    @SerializedName("adult")
    val adult: Boolean,

    @SerializedName("backdrop_path")
    val image: String?,

    @SerializedName("belongs_to_collection")
    val collection: Collection?,

    @SerializedName("budget")
    val budget: Int?,

    @SerializedName("genres")
    val genres: List<Genres>,

    @SerializedName("homepage")
    val homepage: String?,

    @SerializedName("id")
    val id: Int?,

    @SerializedName("imdb_id")
    val imdbId: String?,

    @SerializedName("original_language")
    val originalLanguage: String?,

    @SerializedName("original_title")
    val originalTitle: String?,

    @SerializedName("overview")
    val description: String?,

    @SerializedName("popularity")
    val popularity: Double?,

    @SerializedName("poster_path")
    val poster: String?,

    @SerializedName("production_companies")
    val companies: List<Company>,

    @SerializedName("production_countries")
    val countries: List<Country>,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("revenue")
    val revenue: Long?,

    @SerializedName("runtime")
    val runtime: Int?,

    @SerializedName("spoken_languages")
    val languages: List<Language>,

    @SerializedName("status")
    val status: String?,

    @SerializedName("tagline")
    val tagLine: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("video")
    val video: Boolean,

    @SerializedName("vote_average")
    val rating: Double?,

    @SerializedName("vote_count")
    val voteCount: Int?
) {
    data class Collection(
        @SerializedName("id")
        val id: Long?,

        @SerializedName("name")
        val name: String?,

        @SerializedName("poster_path")
        val poster: String?,

        @SerializedName("backdrop_path")
        val image: String?
    )
}