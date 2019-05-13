package com.example.android.movie.mvp.moviedetails

interface IMovieDetailsPresenter{

    fun onDownloadMovieDetails(movieId: Long)

    fun onDownloadActorSquad(movieId: Long)

    fun onDownloadRecommendedMovies(movieId: Long)

    fun onDestroy()
}