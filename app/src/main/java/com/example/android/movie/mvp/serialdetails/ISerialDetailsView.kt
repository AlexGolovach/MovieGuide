package com.example.android.movie.mvp.serialdetails

import com.example.android.database.model.ActorSquad
import com.example.android.database.model.Details
import com.example.android.database.model.Movie
import com.example.android.database.model.Video
import com.example.android.movie.mvp.base.ILoadingView

interface ISerialDetailsView : ILoadingView {

    fun onDownloadResultDetails(serial: Details)

    fun onDownloadActorSquad(actorSquad: List<ActorSquad>)

    fun onDownloadRecommendedSerials(serials: List<Movie>)

    fun onDownloadVideo(videos: List<Video>)

    fun onDownloadDetailsError(error: String)

    fun isSerialInFavorites(result: Boolean)
}