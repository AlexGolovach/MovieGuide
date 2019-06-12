package com.example.android.network.repository.actors

import com.example.android.network.APIClient
import com.example.android.network.Converter
import com.example.android.network.NetworkCallback
import com.example.android.network.httprequest.HttpRequest
import com.example.android.network.models.actor.Actor
import com.example.android.network.models.actor.ActorImages
import com.example.android.network.models.moviesquad.MovieActorSquad
import com.example.android.network.models.serialsquad.SerialActorSquad
import java.lang.NullPointerException

class ActorsRepositoryImpl : ActorsRepository {

    override fun getInformationAboutActor(actorId: Int, callback: NetworkCallback<Actor>) {

        HttpRequest.getInstance()?.load(APIClient.getInformationAboutActor(actorId), object :
            NetworkCallback<String> {
            override fun onSuccess(result: String) {

                callback.onSuccess(Converter.parsingJson(result, Actor::class.java))
            }

            override fun onError(throwable: Throwable) {
                if (throwable is NullPointerException) {
                    callback.onError(NullPointerException("We have some problems with download information about actor"))
                }
            }
        })
    }

    override fun getActorImages(actorId: Int, callback: NetworkCallback<ActorImages>) {

        HttpRequest.getInstance()?.load(APIClient.getImagesWithActor(actorId), object :
            NetworkCallback<String> {
            override fun onSuccess(result: String) {
                callback.onSuccess(Converter.parsingJson(result, ActorImages::class.java))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(NullPointerException("We have some problems with download profiles"))
            }
        })
    }

    override fun getMovieActorsSquad(movieId: Int, callback: NetworkCallback<MovieActorSquad>) {

        HttpRequest.getInstance()?.load(APIClient.getActorsSquad(movieId), object :
            NetworkCallback<String> {
            override fun onSuccess(result: String) {
                callback.onSuccess(Converter.parsingJson(result, MovieActorSquad::class.java))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(NullPointerException("We have some problems with download actor squad"))
            }
        })
    }

    override fun getSerialActorSquad(serialId: Int, callback: NetworkCallback<SerialActorSquad>) {

        HttpRequest.getInstance()?.load(APIClient.getSerialActorSquad(serialId), object : NetworkCallback<String> {
            override fun onSuccess(result: String) {
                callback.onSuccess(Converter.parsingJson(result, SerialActorSquad::class.java))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(throwable)
            }

        })
    }
}