package com.example.android.movie.mvp.topmovies

import com.example.android.movie.mvp.base.ILoadingView
import com.example.android.network.models.movie.Movie

interface ITopMoviesView: ILoadingView {

    fun onDownloadResult(movies: List<Movie>)

    fun onDownloadError(throwable: Throwable)
}