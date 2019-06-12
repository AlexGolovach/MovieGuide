package com.example.android.movie.mvp.moviedetails

import android.graphics.Bitmap
import com.example.android.database.model.ActorSquad
import com.example.android.database.model.Information
import com.example.android.database.model.Movie
import com.example.android.movie.mvp.base.ILoadingView
import com.example.android.network.models.movie.MovieList
import com.example.android.network.models.moviedetails.MovieDetails
import com.example.android.network.models.moviesquad.MovieActorSquad

interface IMovieDetailsView: ILoadingView {

    fun onDownloadResultDetails(movie: MovieDetails, poster: Bitmap)

    fun onDownloadFromBD(result: Information, bitmap: Bitmap)

    fun onDownloadActorSquad(actorSquad: MovieActorSquad)

    fun onDownloadRecommendedMovies(movies: MovieList)

    fun onDownloadVideo(videos: List<String>)

    fun onDownloadDetailsError(throwable: Throwable)

    fun onDownloadActorSquadFromBd(result: List<ActorSquad>)

    fun onDownloadRecommendedMoviesFromBd(result: List<Movie>)
}