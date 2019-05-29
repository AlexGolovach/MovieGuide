package com.example.android.movie.mvp.showdetails

import com.example.android.database.model.Reviews
import com.example.android.movie.mvp.base.ILoadingView
import com.example.android.network.models.recommendedshows.RecommendShowsList
import com.example.android.network.models.showdetails.ShowDetails
import com.example.android.network.models.showsquad.ActorShowSquad

interface IShowDetailsView : ILoadingView{

    fun onDownloadResultDetails(show: ShowDetails)

    fun onDownloadActorSquad(actorSquad: ActorShowSquad)

    fun onDownloadRecommendedShows(shows: RecommendShowsList)

    fun onDownloadVideo(videos: List<String>)

    fun onDownloadDetailsError(throwable: Throwable)

    fun isMovieAddedInFavorites(result: Boolean)

    fun onDownloadReviewsForShow(result: ArrayList<Reviews>)
}