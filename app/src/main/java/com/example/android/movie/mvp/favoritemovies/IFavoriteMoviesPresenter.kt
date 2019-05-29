package com.example.android.movie.mvp.favoritemovies

import com.example.android.database.model.FavoriteMovies

interface IFavoriteMoviesPresenter {

    fun downloadMyFavorites(userId: String)

    fun deleteMovie(movie: FavoriteMovies)

    fun onDestroy()
}