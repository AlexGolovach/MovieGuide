package com.example.android.movie.mvp.topmovies

interface ITopMoviesPresenter {

    fun onDownloadMovies(page: Int = 1)

    fun onSearchMovies(query: String?)

    fun onDestroy()
}