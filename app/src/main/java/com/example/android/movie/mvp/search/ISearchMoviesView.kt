package com.example.android.movie.mvp.search

import com.example.android.network.models.movie.MovieList

interface ISearchMoviesView{

    fun onSearchResult(movies: MovieList)

    fun onSearchError(throwable: Throwable)
}