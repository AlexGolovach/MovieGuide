package com.example.android.network.repository.shows

import com.example.android.network.models.recommendedshows.RecommendShowsList
import com.example.android.network.models.showdetails.ShowDetails
import com.example.android.network.models.shows.ShowsList
import com.example.android.network.models.shows.actorshows.ActorShows

interface ShowsRepository{

    fun getPopularShows(callback: ShowsCallback<ShowsList>)

    fun getShowDetails(showId: Int, callback: ShowsCallback<ShowDetails>)

    fun getRecommendedShowsForShow(showId: Int, callback: ShowsCallback<RecommendShowsList>)

    fun getActorShows(actorId: Int, callback: ShowsCallback<ActorShows>)

    fun getSearchResultShows(query: String, callback: ShowsCallback<ShowsList>)
}