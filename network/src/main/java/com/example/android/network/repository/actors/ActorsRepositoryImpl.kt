package com.example.android.network.repository.actors

import com.example.android.network.APIClient
import com.example.android.network.Converter
import com.example.android.network.httprequest.Callback
import com.example.android.network.httprequest.HttpRequest
import com.example.android.network.models.actor.Actor
import com.example.android.network.models.actor.ActorImages
import com.example.android.network.models.moviesquad.MovieActorSquad
import java.lang.NullPointerException

class ActorsRepositoryImpl : ActorsRepository {

    override fun getInformationAboutActor(actorId: Int, callback: ActorsCallback<Actor>) {

        HttpRequest.getInstance()?.load(APIClient.getInformationAboutActor(actorId), object : Callback {
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

        HttpRequest.getInstance()?.load(APIClient.getImagesWithActor(actorId), object : Callback {
            override fun onSuccess(json: String) {
                callback.onSuccess(Converter.parsingJson(json, ActorImages::class.java))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(NullPointerException("We have some problems with download profiles"))
            }
        })
    }

    override fun getActorsSquad(movieId: Int, callback: ActorsCallback<MovieActorSquad>) {

        HttpRequest.getInstance()?.load(APIClient.getActorsSquad(movieId), object : Callback {
            override fun onSuccess(json: String) {
                callback.onSuccess(Converter.parsingJson(json, MovieActorSquad::class.java))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(NullPointerException("We have some problems with download actor squad"))
            }
        })
    }
}