package com.example.android.network.models.shows.actorshows

import com.google.gson.annotations.SerializedName

data class Crew(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("department")
    val department: String?,
    @SerializedName("original_language")
    val original_language: String?,
    @SerializedName("original_country")
    val original_country: List<String>,
    @SerializedName("job")
    val job: String?,
    @SerializedName("overview")
    val description: String?,
    @SerializedName("vote_count")
    val vote_count: Int?,
    @SerializedName("poster_path")
    val poster: String?,
    @SerializedName("backdrop_path")
    val image: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("genre_ids")
    val genre_ids: List<Int>,
    @SerializedName("vote_average")
    val rating: Double?,
    @SerializedName("first_air_date")
    val first_air_date: String?,
    @SerializedName("credit_id")
    val credit_id: String?,
    @SerializedName("original_name")
    val original_name: String?,
    @SerializedName("episode_count")
    val episode_count: Int?
)

//"id": 12779,
//      "department": "Writing",
//      "original_language": "en",
//      "episode_count": 1,
//      "job": "Writer",
//      "overview": "Lady Blue is a crime drama starring Jamie Rose as a Chicago female homicide detective Katy Mahoney. The show was produced by MGM/UA Television and aired on ABC-TV from September 26, 1985 to January 25, 1986 for 13 episodes. It was cancelled after one season after low ratings in its Thursday night slot, but also because it was considered too violent for its time. The show later aired on Saturday nights but the show failed there as well.\n\nIt ranked 72nd out of 82 programs that season, and averaged a 10.7 household rating.\n\nIt was said that Katy Mahoney was the female version of Harry Callahan aka Dirty Harry, prompting television critics to refer to Mahoney's character as \"Dirty Harriet\".",
//      "origin_country": [
//        "US"
//      ],
//      "original_name": "Lady Blue",
//      "vote_count": 2,
//      "name": "Lady Blue",
//      "first_air_date": "1985-04-15",
//      "poster_path": "/hvWHCCuOSUa5cWs8Vbo0Msc3b77.jpg",
//      "vote_average": 6.5,
//      "genre_ids": [],
//      "backdrop_path": null,
//      "popularity": 1.199,
//      "credit_id": "59e143cb92514172610038f4"
//    },