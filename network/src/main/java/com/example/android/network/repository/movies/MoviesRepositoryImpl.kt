package com.example.android.network.repository.movies

import android.util.Log
import com.example.android.network.APIClient
import com.example.android.network.Converter
import com.example.android.network.httprequest.Callback
import com.example.android.network.httprequest.HttpRequest
import com.example.android.network.models.moviedetails.MovieDetails
import com.example.android.network.models.movie.MovieList
import com.example.android.network.models.movie.actormovies.ActorMovies
import java.lang.NullPointerException

class MoviesRepositoryImpl : MoviesRepository {

    override fun loadPopularMovies(callback: MoviesCallback<MovieList>) {

        HttpRequest.getInstance()?.load(APIClient.GET_POPULAR_MOVIES, object : Callback {
            override fun onSuccess(json: String) {

                callback.onSuccess(Converter.parsingJson(json, MovieList::class.java))
            }

            override fun onError(throwable: Throwable) {
                if (throwable is NullPointerException) {
                    Log.d(MoviesRepositoryImpl::class.java.simpleName, throwable.message)
                }
            }
        })
    }

    override fun searchMovie(query: String, callback: MoviesCallback<MovieList>) {

        HttpRequest.getInstance()
            ?.load(APIClient.getSearchResultForMovies(query), object : Callback {
                override fun onSuccess(json: String) {

                    callback.onSuccess(Converter.parsingJson(json, MovieList::class.java))
                }

                override fun onError(throwable: Throwable) {
                    if (throwable is NullPointerException) {
                        Log.d(MoviesRepositoryImpl::class.java.simpleName, throwable.message)
                    }
                }
            })
    }

    override fun getInformationAboutMovie(movieId: Int, callback: MoviesCallback<MovieDetails>) {

        HttpRequest.getInstance()
            ?.load(APIClient.getInformationAboutMovie(movieId), object : Callback {
                override fun onSuccess(json: String) {

                    callback.onSuccess(Converter.parsingJson(json, MovieDetails::class.java))
                }

                override fun onError(throwable: Throwable) {
                    if (throwable is NullPointerException) {
                        Log.d(MoviesRepositoryImpl::class.java.simpleName, throwable.message)
                    }
                }
            })
    }

    override fun getActorMovies(actorId: Int, callback: MoviesCallback<ActorMovies>) {

        HttpRequest.getInstance()?.load(APIClient.getMoviesWithActor(actorId), object : Callback {
            override fun onSuccess(json: String) {
                callback.onSuccess(Converter.parsingJson(json, ActorMovies::class.java))
            }

            override fun onError(throwable: Throwable) {
                if (throwable is NullPointerException) {
                    Log.d(MoviesRepositoryImpl::class.java.simpleName, throwable.message)
                }
            }
        })
    }

    override fun getRecommendedMoviesForMovie(
        movieId: Int,
        callback: MoviesCallback<MovieList>
    ) {

        HttpRequest.getInstance()
            ?.load(APIClient.getRecommendedMoviesForMovie(movieId), object : Callback {
                override fun onSuccess(json: String) {

                    callback.onSuccess(Converter.parsingJson(json, MovieList::class.java))
                }

                override fun onError(throwable: Throwable) {
                    if (throwable is NullPointerException) {
                        Log.d(MoviesRepositoryImpl::class.java.simpleName, throwable.message)
                    }
                }
            })
    }

}