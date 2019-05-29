package com.example.android.movie.mvp.moviedetails

import com.example.android.database.model.Reviews
import com.example.android.movie.mvp.base.ILoadingView
import com.example.android.network.models.movie.MovieList
import com.example.android.network.models.moviedetails.MovieDetails
import com.example.android.network.models.moviesquad.MovieActorSquad

interface IMovieDetailsView: ILoadingView {

    fun onDownloadResultDetails(movie: MovieDetails)

    fun onDownloadActorSquad(actorSquad: MovieActorSquad)

    fun onDownloadRecommendedMovies(movies: MovieList)

    fun onDownloadVideo(videos: List<String>)

    fun onDownloadDetailsError(throwable: Throwable)

    fun isMovieAddedInFavorites(result: Boolean)

    fun onDownloadReviewsForMovie(result: ArrayList<Reviews>)
}