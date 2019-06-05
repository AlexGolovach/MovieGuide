package com.example.android.movie.mvp.topshows

interface ITopShowsPresenter {

    fun onDownloadShows()

    fun onSearchMovies(query: String?)

    fun onDestroy()
}