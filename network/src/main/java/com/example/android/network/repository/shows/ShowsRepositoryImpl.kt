package com.example.android.network.repository.shows

import android.util.Log
import com.example.android.network.Constants
import com.example.android.network.Converter
import com.example.android.network.httprequest.Callback
import com.example.android.network.httprequest.HttpRequest
import com.example.android.network.models.recommendedshows.RecommendShowsList
import com.example.android.network.models.showdetails.ShowDetails
import com.example.android.network.models.shows.ShowsList
import com.example.android.network.models.shows.actorshows.ActorShows

class ShowsRepositoryImpl : ShowsRepository {

    private lateinit var url: String

    override fun getPopularShows(callback: ShowsCallback<ShowsList>) {
        url =
            "https://api.themoviedb.org/3/tv/popular?api_key=${Constants.API_KEY}&language=en-US&page=1"

        HttpRequest.getInstance()?.load(url, object : Callback {
            override fun onSuccess(json: String) {
                callback.onSuccess(Converter.parsingJson(json, ShowsList::class.java))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(throwable)
            }
        })
    }

    override fun getShowDetails(showId: Int, callback: ShowsCallback<ShowDetails>) {
        url = "https://api.themoviedb.org/3/tv/$showId?api_key=${Constants.API_KEY}&language=en-US"

        HttpRequest.getInstance()?.load(url, object : Callback {
            override fun onSuccess(json: String) {
                Log.d(ShowsRepositoryImpl::class.java.name, json)

                callback.onSuccess(Converter.parsingJson(json, ShowDetails::class.java))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(throwable)
            }
        })
    }

    override fun getRecommendedShowsForShow(
        showId: Int,
        callback: ShowsCallback<RecommendShowsList>
    ) {
        url =
            "https://api.themoviedb.org/3/tv/$showId/recommendations?api_key=${Constants.API_KEY}&language=en-US&page=1"

        HttpRequest.getInstance()?.load(url, object : Callback {
            override fun onSuccess(json: String) {
                callback.onSuccess(Converter.parsingJson(json, RecommendShowsList::class.java))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(throwable)
            }
        })
    }

    override fun getActorShows(actorId: Int, callback: ShowsCallback<ActorShows>) {
        url =
            "https://api.themoviedb.org/3/person/$actorId/tv_credits?api_key=${Constants.API_KEY}&language=en-US"

        HttpRequest.getInstance()?.load(url, object : Callback {
            override fun onSuccess(json: String) {
                callback.onSuccess(Converter.parsingJson(json, ActorShows::class.java))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(throwable)
            }
        })
    }

    override fun getSearchResultShows(query: String, callback: ShowsCallback<ShowsList>) {
        url =
            "https://api.themoviedb.org/3/search/tv?api_key=${Constants.API_KEY}&language=en-US&query=$query&page=1"

        HttpRequest.getInstance()?.load(url, object : Callback {
            override fun onSuccess(json: String) {
                callback.onSuccess(Converter.parsingJson(json, ShowsList::class.java))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(throwable)
            }
        })
    }
}