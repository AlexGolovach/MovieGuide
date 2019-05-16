package com.example.android.network.repository.movies

import android.util.Log
import com.example.android.network.Constants
import com.example.android.network.Converter
import com.example.android.network.httprequest.Callback
import com.example.android.network.httprequest.HttpRequest
import com.example.android.network.models.movie.Movie
import com.example.android.network.models.moviedetails.MovieDetails
import com.example.android.network.models.movie.MovieList
import com.example.android.network.models.movie.actormovies.ActorMovies
import org.json.JSONObject
import java.lang.NullPointerException

class MoviesRepositoryImpl : MoviesRepository {

    private lateinit var url: String

    override fun loadPopularMovies(callback: MoviesCallback<MovieList>) {
        url =
            "https://api.themoviedb.org/3/movie/popular?api_key=${Constants.API_KEY}&language=en-US&page=1"

        HttpRequest.getInstance()?.load(url, object : Callback {
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

        url =
            "https://api.themoviedb.org/3/search/movie?api_key=${Constants.API_KEY}&language=en-US&query=$query&page=1&include_adult=false"

        HttpRequest.getInstance()?.load(url, object : Callback {
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
        url =
            "https://api.themoviedb.org/3/movie/$movieId?api_key=${Constants.API_KEY}&language=en-US"

        HttpRequest.getInstance()?.load(url, object : Callback {
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

        url =
            "https://api.themoviedb.org/3/person/$actorId/movie_credits?api_key=${Constants.API_KEY}&language=en-US"

        HttpRequest.getInstance()?.load(url, object : Callback {
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

        url =
            "https://api.themoviedb.org/3/movie/$movieId/recommendations?api_key=${Constants.API_KEY}&language=en-US&page=1"

        HttpRequest.getInstance()?.load(url, object : Callback {
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