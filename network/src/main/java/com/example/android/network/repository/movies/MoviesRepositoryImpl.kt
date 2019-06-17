package com.example.android.network.repository.movies

import com.example.android.network.APIClient
import com.example.android.network.Converter
import com.example.android.network.NetworkCallback
import com.example.android.network.httprequest.HttpRequest
import com.example.android.network.models.movie.MovieList
import com.example.android.network.models.movie.actormovies.ActorMovies
import com.example.android.network.models.moviedetails.MovieDetails

internal class MoviesRepositoryImpl : MoviesRepository {

    override fun loadPopularMovies(page: Int, callback: NetworkCallback<MovieList>) {

        HttpRequest.getInstance()?.load(APIClient.getPopularMovies(page), object :
            NetworkCallback<String> {
            override fun onSuccess(result: String) {

                callback.onSuccess(Converter.parsingJson(result, MovieList::class.java))
            }

            override fun onError(error: String) {
                callback.onError(error)
            }
        })
    }

    override fun searchMovie(query: String, callback: NetworkCallback<MovieList>) {

        HttpRequest.getInstance()
            ?.load(APIClient.getSearchResultForMovies(query), object :
                NetworkCallback<String> {
                override fun onSuccess(result: String) {

                    callback.onSuccess(Converter.parsingJson(result, MovieList::class.java))
                }

                override fun onError(error: String) {
                    callback.onError(error)
                }
            })
    }

    override fun getInformationAboutMovie(movieId: Int, callback: NetworkCallback<MovieDetails>) {

        HttpRequest.getInstance()
            ?.load(APIClient.getInformationAboutMovie(movieId), object :
                NetworkCallback<String> {
                override fun onSuccess(result: String) {

                    callback.onSuccess(Converter.parsingJson(result, MovieDetails::class.java))
                }

                override fun onError(error: String) {
                    callback.onError(error)
                }
            })
    }

    override fun getActorMovies(actorId: Int, callback: NetworkCallback<ActorMovies>) {

        HttpRequest.getInstance()?.load(APIClient.getMoviesWithActor(actorId), object :
            NetworkCallback<String> {
            override fun onSuccess(result: String) {
                callback.onSuccess(Converter.parsingJson(result, ActorMovies::class.java))
            }

            override fun onError(error: String) {
                callback.onError(error)
            }
        })
    }

    override fun getRecommendedMoviesForMovie(
        movieId: Int,
        callback: NetworkCallback<MovieList>
    ) {

        HttpRequest.getInstance()
            ?.load(APIClient.getRecommendedMoviesForMovie(movieId), object :
                NetworkCallback<String> {
                override fun onSuccess(result: String) {

                    callback.onSuccess(Converter.parsingJson(result, MovieList::class.java))
                }

                override fun onError(error: String) {
                    callback.onError(error)
                }
            })
    }

}