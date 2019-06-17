package com.example.android.movie.mvp.actor

import com.example.android.database.model.Movie
import com.example.android.movie.mvp.base.ILoadingView

interface IActorView: ILoadingView {

    fun onDownloadResultDetails(actor: com.example.android.database.model.Actor)

    fun onDownloadImageURLs(images: List<String>)

    fun onDownloadActorMovies(movies: List<Movie>)

    fun onDownloadDetailsError(error: String)
}