package com.example.android.movie.mvp.moviedetails

import com.example.android.database.model.ActorSquad
import com.example.android.database.model.Details
import com.example.android.database.model.Movie
import com.example.android.database.model.Video
import com.example.android.movie.mvp.base.ILoadingView

interface IMovieDetailsView: ILoadingView {

    fun onDownloadResultDetails(movie: Details)

    fun onDownloadActorSquad(actorSquad: List<ActorSquad>)

    fun onDownloadRecommendedMovies(movies: List<Movie>)

    fun onDownloadVideo(videos: List<Video>)

    fun onDownloadDetailsError(error: String)

    fun isMovieInFavorites(result: Boolean)
}