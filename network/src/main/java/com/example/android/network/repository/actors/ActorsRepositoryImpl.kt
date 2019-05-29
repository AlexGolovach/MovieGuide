package com.example.android.network.repository.actors

import com.example.android.network.Constants
import com.example.android.network.Converter
import com.example.android.network.httprequest.Callback
import com.example.android.network.httprequest.HttpRequest
import com.example.android.network.models.actor.Actor
import com.example.android.network.models.actor.ActorImages
import com.example.android.network.models.moviesquad.MovieActorSquad
import com.example.android.network.models.showsquad.ActorShowSquad
import java.lang.NullPointerException

class ActorsRepositoryImpl : ActorsRepository {

    private lateinit var url: String

    override fun getInformationAboutActor(actorId: Int, callback: ActorsCallback<Actor>) {
        url =
            "https://api.themoviedb.org/3/person/$actorId?api_key=${Constants.API_KEY}&language=en-US"

        HttpRequest.getInstance()?.load(url, object : Callback {
            override fun onSuccess(json: String) {

                callback.onSuccess(Converter.parsingJson(json, Actor::class.java))
            }

            override fun onError(throwable: Throwable) {
                if (throwable is NullPointerException) {
                    callback.onError(NullPointerException("We have some problems with download information about actor"))
                }
            }
        })
    }

    override fun getActorImages(actorId: Int, callback: ActorsCallback<ActorImages>) {

        url = "https://api.themoviedb.org/3/person/$actorId/images?api_key=${Constants.API_KEY}"

        HttpRequest.getInstance()?.load(url, object : Callback {
            override fun onSuccess(json: String) {
                callback.onSuccess(Converter.parsingJson(json, ActorImages::class.java))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(NullPointerException("We have some problems with download profiles"))
            }
        })
    }

    override fun getMovieActorsSquad(movieId: Int, callback: ActorsCallback<MovieActorSquad>) {

        url = "https://api.themoviedb.org/3/movie/$movieId/credits?api_key=${Constants.API_KEY}"

        HttpRequest.getInstance()?.load(url, object : Callback {
            override fun onSuccess(json: String) {
                callback.onSuccess(Converter.parsingJson(json, MovieActorSquad::class.java))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(NullPointerException("We have some problems with download actor squad"))
            }
        })
    }

    override fun getShowActorSquad(showId: Int, callback: ActorsCallback<ActorShowSquad>) {
        url = "https://api.themoviedb.org/3/tv/$showId/credits?api_key=${Constants.API_KEY}"

        HttpRequest.getInstance()?.load(url, object : Callback {
            override fun onSuccess(json: String) {
                callback.onSuccess(Converter.parsingJson(json, ActorShowSquad::class.java))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(throwable)
            }

        })
    }
}