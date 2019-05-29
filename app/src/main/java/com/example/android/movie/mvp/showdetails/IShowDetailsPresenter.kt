package com.example.android.movie.mvp.showdetails

interface IShowDetailsPresenter {

    fun onDownloadShowDetails(showId: Int)

    fun onDownloadActorSquad(showId: Int)

    fun onDownloadRecommendedShows(showId: Int)

    fun onDownloadVideo(showId: Int)

    fun addShowToFavorites(showId: Int)

    fun checkShowInFavorites(showId: Int)

    fun addReviewForShow(showId: Int, userName: String, review: String)

    fun loadReviewForShow(showId: Int)

    fun deleteReviewForShow(documentId: String)

    fun onDestroy()
}