package com.example.android.movie.mvp.actor

interface IActorPresenter{

    fun onDownloadActorDetails(actorId: Long)

    fun onDownloadImageURLs(actorId: Long)

    fun onDownloadActorMovies(actorId: Long)

    fun onDestroy()
}