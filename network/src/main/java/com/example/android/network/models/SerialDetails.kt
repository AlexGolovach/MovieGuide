package com.example.android.network.models

import com.google.gson.annotations.SerializedName

data class SerialDetails(
    @SerializedName("backdrop_path")
    val image: String?,

    @SerializedName("created_by")
    val createdBy: List<Creator>,

    @SerializedName("episode_run_time")
    val episodeTime: List<Int>,

    @SerializedName("first_air_date")
    val releaseDate: String?,

    @SerializedName("genres")
    val genres: List<Genres>,

    @SerializedName("homepage")
    val homepage: String?,

    @SerializedName("id")
    val id: Int?,

    @SerializedName("in_production")
    val inProduction: Boolean,

    @SerializedName("language")
    val language: List<String>,

    @SerializedName("last_air_date")
    val lastAirDate: String?,

    @SerializedName("last_episode_to_air")
    val lastEpisodeToAir: LastEpisode,

    @SerializedName("name")
    val name: String?,

    @SerializedName("next_episode_to_air")
    val nextEpisode: NextEpisode?,

    @SerializedName("networks")
    val networks: List<Networks>,

    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int?,

    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int?,

    @SerializedName("origin_country")
    val originCountry: List<String>,

    @SerializedName("original_language")
    val originalLanguage: String?,

    @SerializedName("original_name")
    val originalName: String?,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("popularity")
    val popularity: Double?,

    @SerializedName("poster_path")
    val poster: String?,

    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanies>,

    @SerializedName("seasons")
    val seasons: List<Season>,

    @SerializedName("status")
    val status: String?,

    @SerializedName("type")
    val type: String?,

    @SerializedName("vote_average")
    val rating: Double?,

    @SerializedName("vote_count")
    val voteCount: Int?

) {

    data class Creator(
        @SerializedName("id")
        val id: Int?,

        @SerializedName("credit_id")
        val creditId: String?,

        @SerializedName("name")
        val name: String?,

        @SerializedName("gender")
        val gender: Int?,

        @SerializedName("profile_path")
        val profileImage: String?
    )

    data class LastEpisode(
        @SerializedName("air_date")
        val airDate: String?,

        @SerializedName("episode_number")
        val episodeNumber: Int?,

        @SerializedName("id")
        val id: Int?,

        @SerializedName("name")
        val name: String?,

        @SerializedName("overview")
        val overview: String?,

        @SerializedName("production_code")
        val productionCode: String?,

        @SerializedName("season_number")
        val seasonNumber: Int?,

        @SerializedName("show_id")
        val showId: Int?,

        @SerializedName("still_path")
        val image: String?,

        @SerializedName("vote_average")
        val rating: Double?,

        @SerializedName("vote_count")
        val voteCount: Int?
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
        val originCountry: String?
    )

    data class Season(
        @SerializedName("air_date")
        val airDate: String?,

        @SerializedName("episode_count")
        val episodeCount: Int?,

        @SerializedName("id")
        val id: Int?,

        @SerializedName("name")
        val name: String?,

        @SerializedName("overview")
        val overview: String?,

        @SerializedName("poster_path")
        val poster: String?,

        @SerializedName("season_number")
        val seasonNumber: Int?
    )

    data class NextEpisode(
        @SerializedName("air_date")
        val airDate: String?,

        @SerializedName("episode_number")
        val episodeNumber: Int?,

        @SerializedName("id")
        val id: Int?,

        @SerializedName("name")
        val name: String?,

        @SerializedName("overview")
        val overview: String?,

        @SerializedName("production_code")
        val productionCode: String?,

        @SerializedName("season_number")
        val seasonNumber: Int?,

        @SerializedName("show_id")
        val showId: Int?,

        @SerializedName("still_path")
        val stillPath: String?,

        @SerializedName("vote_average")
        val voteAverage: Double?,

        @SerializedName("vote_count")
        val voteCount: Double?
    )
}