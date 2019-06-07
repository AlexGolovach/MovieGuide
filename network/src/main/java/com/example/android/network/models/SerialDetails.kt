package com.example.android.network.models

import com.google.gson.annotations.SerializedName

data class SerialDetails(
    @SerializedName("backdrop_path")
    val image: String?,
    @SerializedName("created_by")
    val created_by: List<Creator>,
    @SerializedName("episode_run_time")
    val episode_time: List<Int>,
    @SerializedName("first_air_date")
    val release_date: String?,
    @SerializedName("genres")
    val genres: List<Genres>,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("in_production")
    val in_production: Boolean,
    @SerializedName("language")
    val language: List<String>,
    @SerializedName("last_air_date")
    val last_air_date: String?,
    @SerializedName("last_episode_to_air")
    val last_episode_to_air: LastEpisode,
    @SerializedName("name")
    val name: String?,
    @SerializedName("next_episode_to_air")
    val next_episode: NextEpisode?,
    @SerializedName("networks")
    val networks: List<Networks>,
    @SerializedName("number_of_episodes")
    val number_of_episodes: Int?,
    @SerializedName("number_of_seasons")
    val number_of_seasons: Int?,
    @SerializedName("origin_country")
    val origin_country: List<String>,
    @SerializedName("original_language")
    val original_language: String?,
    @SerializedName("original_name")
    val original_name: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val poster: String?,
    @SerializedName("production_companies")
    val production_companies: List<ProductionCompanies>,
    @SerializedName("seasons")
    val seasons: List<Season>,
    @SerializedName("status")
    val status: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("vote_average")
    val rating: Double?,
    @SerializedName("vote_count")
    val vote_count: Int?

) {

    data class Creator(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("credit_id")
        val credit_id: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("gender")
        val gender: Int?,
        @SerializedName("profile_path")
        val profile_image: String?
    )

    data class LastEpisode(
        @SerializedName("air_date")
        val air_date: String?,
        @SerializedName("episode_number")
        val episode_number: Int?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("overview")
        val overview: String?,
        @SerializedName("production_code")
        val production_code: String?,
        @SerializedName("season_number")
        val season_number: Int?,
        @SerializedName("show_id")
        val showId: Int?,
        @SerializedName("still_path")
        val image: String?,
        @SerializedName("vote_average")
        val rating: Double?,
        @SerializedName("vote_count")
        val vote_count: Int?
    )

    data class Networks(
        @SerializedName("name")
        val name: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("logo_path")
        val image: String?,
        @SerializedName("origin_country")
        val country: String?
    )

    data class ProductionCompanies(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("logo_path")
        val image: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("origin_country")
        val origin_country: String?
    )

    data class Season(
        @SerializedName("air_date")
        val air_date: String?,
        @SerializedName("episode_count")
        val episode_count: Int?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("overview")
        val overview: String?,
        @SerializedName("poster_path")
        val poster: String?,
        @SerializedName("season_number")
        val season_number: Int?
    )

    data class NextEpisode(
        @SerializedName("air_date")
        val air_date: String?,
        @SerializedName("episode_number")
        val episode_number: Int?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("overview")
        val overview: String?,
        @SerializedName("production_code")
        val production_code: String?,
        @SerializedName("season_number")
        val season_number: Int?,
        @SerializedName("show_id")
        val showId: Int?,
        @SerializedName("still_path")
        val still_path: String?,
        @SerializedName("vote_average")
        val vote_average: Double?,
        @SerializedName("vote_count")
        val vote_count: Double?
    )
}