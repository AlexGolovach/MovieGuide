package com.example.android.network.repository.movies

import com.example.android.network.NetworkCallback
import com.example.android.network.models.movie.MovieList
import com.example.android.network.models.movie.actormovies.ActorMovies
import com.example.android.network.models.moviedetails.MovieDetails

interface MoviesRepository {

    fun loadPopularMovies(callback: NetworkCallback<MovieList>)

    fun searchMovie(query: String, callback: NetworkCallback<MovieList>)

    fun getInformationAboutMovie(movieId: Int, callback: NetworkCallback<MovieDetails>)

    fun getActorMovies(actorId: Int, callback: NetworkCallback<ActorMovies>)

    fun getRecommendedMoviesForMovie(movieId: Int, callback: NetworkCallback<MovieList>)
}