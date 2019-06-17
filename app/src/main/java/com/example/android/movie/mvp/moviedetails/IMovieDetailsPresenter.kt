package com.example.android.movie.mvp.moviedetails

interface IMovieDetailsPresenter{

    fun onDownloadMovieDetails(movieId: Int)

    fun onDownloadActorSquad(movieId: Int)

    fun onDownloadRecommendedMovies(movieId: Int)

    fun onDownloadVideo(movieId: Int)

    fun addToFavorite(movieId: Int, type: String)

    fun checkMovieInFavorites(userId: Int, movieId: Int)

    fun deleteFromFavorite(userId: Int, movieId: Int)

    fun onDestroy()
}