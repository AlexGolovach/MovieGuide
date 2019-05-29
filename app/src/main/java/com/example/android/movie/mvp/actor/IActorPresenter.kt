package com.example.android.movie.mvp.actor

interface IActorPresenter{

    fun onDownloadActorDetails(actorId: Int)

    fun onDownloadImageURLs(actorId: Int)

    fun onDownloadActorMovies(actorId: Int)

    fun onDownloadActorShows(actorId: Int)

    fun onDestroy()
}