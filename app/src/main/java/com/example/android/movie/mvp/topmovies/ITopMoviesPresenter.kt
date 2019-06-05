package com.example.android.movie.mvp.topmovies

interface ITopMoviesPresenter {

    fun onDownloadMovies()

    fun onSearchMovies(query: String?)

    fun onDestroy()
}