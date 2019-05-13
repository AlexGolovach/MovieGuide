package com.example.android.movie.mvp.actor

import android.graphics.Bitmap
import com.example.android.movie.mvp.base.ILoadingView
import com.example.android.network.models.actor.Actor
import com.example.android.network.models.movie.Movie

interface IActorView: ILoadingView {

    fun onDownloadResultDetails(actor: Actor, image: Bitmap)

    fun onDownloadImageURLs(images: List<String>)

    fun onDownloadActorMovies(movies: List<Movie>)

    fun onDownloadDetailsError(throwable: Throwable)
}