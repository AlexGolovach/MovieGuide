package com.example.android.movie.mvp.search

interface ISearchMoviesPresenter {

    fun onSearchMovies(query: String?)

    fun onDestroy()
}