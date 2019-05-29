package com.example.android.movie.mvp.moviedetails

interface IMovieDetailsPresenter{

    fun onDownloadMovieDetails(movieId: Int)

    fun onDownloadActorSquad(movieId: Int)

    fun onDownloadRecommendedMovies(movieId: Int)

    fun onDownloadVideo(movieId: Int)

    fun addMovieToFavorites(movieId: Int)

    fun checkMovieInFavorites(movieId: Int)

    fun addReviewForFilm(movieId: Int, userName: String, review: String)

    fun loadReviewForFilm(movieId: Int)

    fun deleteReviewForFilm(documentId: String)

    fun onDestroy()
}