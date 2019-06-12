package com.example.android.movie.mvp.serialdetails

import com.example.android.movie.mvp.base.ILoadingView
import com.example.android.network.models.SerialDetails
import com.example.android.network.models.recommendedserials.RecommendSerialsList
import com.example.android.network.models.serialsquad.SerialActorSquad

interface ISerialDetailsView : ILoadingView{

    fun onDownloadResultDetails(serial: SerialDetails)

    fun onDownloadActorSquad(actorSquad: SerialActorSquad)

    fun onDownloadRecommendedSerials(serials: RecommendSerialsList)

    fun onDownloadVideo(videos: List<String>)

    fun onDownloadDetailsError(throwable: Throwable)
}