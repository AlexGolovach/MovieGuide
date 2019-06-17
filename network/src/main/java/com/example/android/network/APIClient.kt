package com.example.android.network

internal object APIClient {
    const val IMAGE_URL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2"
    private const val FIRST_PART_QUERY = "https://api.themoviedb.org/3/"

    private const val API_KEY = BuildConfig.API_KEY

    fun getPopularMovies(page: Int): String {
        return "${FIRST_PART_QUERY}movie/popular?api_key=$API_KEY&language=en-US&page=$page"
    }

    fun getPopularSerials(page: Int): String{
       return "${FIRST_PART_QUERY}tv/popular?api_key=$API_KEY&language=en-US&page=$page"
    }

    fun getActorsSquad(movieId: Int): String {
        return "${FIRST_PART_QUERY}movie/$movieId/credits?api_key=$API_KEY"
    }

    fun getImagesWithActor(actorId: Int): String {
        return "${FIRST_PART_QUERY}person/$actorId/images?api_key=$API_KEY"
    }

    fun getInformationAboutActor(actorId: Int): String {
        return "${FIRST_PART_QUERY}person/$actorId?api_key=$API_KEY&language=en-US"
    }

    fun getSearchResultForMovies(query: String): String {
        return "${FIRST_PART_QUERY}search/movie?api_key=$API_KEY&language=en-US&query=$query&page=1&include_adult=false"
    }

    fun getInformationAboutMovie(movieId: Int): String {
        return "${FIRST_PART_QUERY}movie/$movieId?api_key=$API_KEY&language=en-US"
    }

    fun getMoviesWithActor(actorId: Int): String {
        return "${FIRST_PART_QUERY}person/$actorId/movie_credits?api_key=$API_KEY&language=en-US"
    }

    fun getRecommendedMoviesForMovie(movieId: Int): String {
        return "${FIRST_PART_QUERY}movie/$movieId/recommendations?api_key=$API_KEY&language=en-US&page=1"
    }

    fun getVideoForMovie(movieId: Int): String {
        return "${FIRST_PART_QUERY}movie/$movieId/videos?api_key=$API_KEY&language=en-US"
    }

    fun getSerialDetails(serialId: Int): String {
        return "${FIRST_PART_QUERY}tv/$serialId?api_key=$API_KEY&language=en-US"
    }

    fun getRecommendedSerialsForSerial(serialId: Int): String {
        return "${FIRST_PART_QUERY}tv/$serialId/recommendations?api_key=$API_KEY&language=en-US&page=1"
    }

    fun getSerialsWithActor(actorId: Int): String {
        return "${FIRST_PART_QUERY}person/$actorId/tv_credits?api_key=$API_KEY&language=en-US"
    }

    fun getSearchResultForSerials(query: String): String {
        return "${FIRST_PART_QUERY}search/tv?api_key=$API_KEY&language=en-US&query=$query&page=1"
    }

    fun getSerialActorSquad(serialId: Int): String {
        return "${FIRST_PART_QUERY}tv/$serialId/credits?api_key=$API_KEY"
    }

    fun getVideoForSerial(serialId: Int): String {
        return "${FIRST_PART_QUERY}tv/$serialId/videos?api_key=$API_KEY&language=en-US"
    }
}