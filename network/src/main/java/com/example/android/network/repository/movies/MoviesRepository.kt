package com.example.android.network.repository.movies

import com.example.android.network.models.movie.Movie
import com.example.android.network.models.movie.MovieDetails

interface MoviesRepository {

    fun loadMovies(callback: MoviesCallback<List<Movie>>)

    fun searchMovie(query: String, callback: MoviesCallback<List<Movie>>)

    fun getInformationAboutMovie(movieId: Long, callback: MoviesCallback<MovieDetails>)

    fun getActorMovies(actorId: Long, callback: MoviesCallback<List<Movie>>)

    fun getRecommendedMoviesForMovie(movieId: Long, callback: MoviesCallback<List<Movie>>)
}