package com.example.android.movie.mvp.moviedetails

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.example.android.movie.mvp.base.ILoadingView
import com.example.android.network.models.movie.Movie
import com.example.android.network.models.movie.MovieActorSquad
import com.example.android.network.models.movie.MovieDetails

interface IMovieDetailsView: ILoadingView {

    fun onDownloadResultDetails(movie: MovieDetails, poster: Bitmap)

    fun onDownloadActorSquad(actorSquad: List<MovieActorSquad>)

    fun onDownloadRecommendedMovies(movies: List<Movie>)

    fun onDownloadDetailsError(throwable: Throwable)
}