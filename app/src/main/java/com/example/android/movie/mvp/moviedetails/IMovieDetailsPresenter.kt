package com.example.android.movie.mvp.moviedetails

interface IMovieDetailsPresenter{

    fun onDownloadMovieDetails(movieId: Int)

    fun onDownloadActorSquad(movieId: Int)

    fun onDownloadRecommendedMovies(movieId: Int)

    fun onDestroy()
}