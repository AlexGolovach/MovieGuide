package com.example.android.movie.mvp.topmovies

import com.example.android.movie.mvp.base.ILoadingView
import com.example.android.network.models.movie.MovieList

interface ITopMoviesView: ILoadingView {

    fun onDownloadResult(movies: MovieList)

    fun onDownloadError(error: String)

    fun onSearchResult(result: MovieList)
}