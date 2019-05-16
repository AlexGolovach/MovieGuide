package com.example.android.network.repository.movies

import com.example.android.network.models.movie.MovieList
import com.example.android.network.models.movie.actormovies.ActorMovies
import com.example.android.network.models.moviedetails.MovieDetails

interface MoviesRepository {

    fun loadPopularMovies(callback: MoviesCallback<MovieList>)

    fun searchMovie(query: String, callback: MoviesCallback<MovieList>)

    fun getInformationAboutMovie(movieId: Int, callback: MoviesCallback<MovieDetails>)

    fun getActorMovies(actorId: Int, callback: MoviesCallback<ActorMovies>)

    fun getRecommendedMoviesForMovie(movieId: Int, callback: MoviesCallback<MovieList>)
}