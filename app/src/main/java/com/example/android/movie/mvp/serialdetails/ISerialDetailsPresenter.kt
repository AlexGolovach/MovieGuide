package com.example.android.movie.mvp.serialdetails

interface ISerialDetailsPresenter {

    fun onDownloadSerialDetails(serialId: Int)

    fun onDownloadActorSquad(serialId: Int)

    fun onDownloadRecommendedSerials(serialId: Int)

    fun onDownloadVideo(serialId: Int)

    fun onDestroy()
}