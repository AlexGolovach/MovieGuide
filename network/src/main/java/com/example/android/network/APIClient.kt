package com.example.android.network

object APIClient{
    const val IMAGE_URL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2"
    private const val API_KEY = "9a1d8d11b865412039d480462d70bcd5"

    const val GET_POPULAR_MOVIES = "https://api.themoviedb.org/3/movie/popular?api_key=$API_KEY&language=en-US&page=1"

    fun getActorsSquad(movieId: Int): String{
        return "https://api.themoviedb.org/3/movie/$movieId/credits?api_key=$API_KEY"
    }

    fun getImagesWithActor(actorId: Int): String{
        return "https://api.themoviedb.org/3/person/$actorId/images?api_key=$API_KEY"
    }

    fun getInformationAboutActor(actorId: Int): String{
        return "https://api.themoviedb.org/3/person/$actorId?api_key=$API_KEY&language=en-US"
    }

    fun getSearchResultForMovies(query: String): String{
        return "https://api.themoviedb.org/3/search/movie?api_key=$API_KEY&language=en-US&query=$query&page=1&include_adult=false"
    }

    fun getInformationAboutMovie(movieId: Int): String{
        return "https://api.themoviedb.org/3/movie/$movieId?api_key=$API_KEY&language=en-US"
    }

    fun getMoviesWithActor(actorId: Int): String{
        return "https://api.themoviedb.org/3/person/$actorId/movie_credits?api_key=$API_KEY&language=en-US"
    }

    fun getRecommendedMoviesForMovie(movieId: Int): String{
        return "https://api.themoviedb.org/3/movie/$movieId/recommendations?api_key=$API_KEY&language=en-US&page=1"
    }

    fun getVideoForMovie(movieId: Int): String{
        return "https://api.themoviedb.org/3/movie/$movieId/videos?api_key=$API_KEY&language=en-US"
    }
}