package com.example.android.movie.mvp.moviedetails

import android.graphics.Bitmap
import com.example.android.movie.mvp.base.ILoadingView
import com.example.android.network.models.movie.MovieList
import com.example.android.network.models.moviedetails.MovieDetails
import com.example.android.network.models.moviesquad.MovieActorSquad

interface IMovieDetailsView: ILoadingView {

    fun onDownloadResultDetails(movie: MovieDetails, poster: Bitmap)

    fun onDownloadActorSquad(actorSquad: MovieActorSquad)

    fun onDownloadRecommendedMovies(movies: MovieList)

    fun onDownloadDetailsError(throwable: Throwable)
}