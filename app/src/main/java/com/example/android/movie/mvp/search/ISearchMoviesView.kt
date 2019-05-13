package com.example.android.movie.mvp.search

import com.example.android.network.models.movie.Movie

interface ISearchMoviesView{

    fun onSearchResult(movies: List<Movie>)

    fun onSearchError(throwable: Throwable)
}