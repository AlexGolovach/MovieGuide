package com.example.android.database.repository.favoritemovies

import com.example.android.database.Callback
import com.example.android.database.model.FavoriteMovies
import com.example.android.network.models.moviedetails.MovieDetails

interface FavoriteMoviesRepository {

    fun addMovieInFavorites(userId: String, movie: MovieDetails)

    fun downloadMyFavoriteMovies(userId: String, callback: Callback<ArrayList<FavoriteMovies>>)

    fun deleteMovie(movie: FavoriteMovies, callback: Callback<String>)

    fun checkMovieInFavorites(movieId: Int, callback: Callback<Boolean>)
}