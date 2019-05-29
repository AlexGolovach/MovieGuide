package com.example.android.movie.mvp.actor

import com.example.android.movie.mvp.base.ILoadingView
import com.example.android.network.models.actor.Actor
import com.example.android.network.models.actor.ActorImages
import com.example.android.network.models.movie.actormovies.ActorMovies
import com.example.android.network.models.shows.actorshows.ActorShows

interface IActorView: ILoadingView {

    fun onDownloadResultDetails(actor: Actor)

    fun onDownloadImageURLs(images: ActorImages)

    fun onDownloadActorMovies(movies: ActorMovies)

    fun onDownloadDetailsError(throwable: Throwable)

    fun onDownloadActorShows(shows: ActorShows)
}