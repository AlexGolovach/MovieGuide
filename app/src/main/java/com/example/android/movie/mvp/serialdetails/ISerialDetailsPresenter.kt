package com.example.android.movie.mvp.serialdetails

interface ISerialDetailsPresenter {

    fun onDownloadSerialDetails(serialId: Int)

    fun onDownloadActorSquad(serialId: Int)

    fun onDownloadRecommendedSerials(serialId: Int)

    fun onDownloadVideo(serialId: Int)

    fun addToFavorite(serialId: Int, type: String)

    fun checkSerialInFavorites(userId: Int, serialId: Int)

    fun deleteFromFavorite(userId: Int, serialId: Int)

    fun onDestroy()
}