package com.example.android.network.repository.actors

import com.example.android.network.NetworkCallback
import com.example.android.network.models.actor.Actor
import com.example.android.network.models.actor.ActorImages
import com.example.android.network.models.moviesquad.MovieActorSquad

interface ActorsRepository{

    fun getInformationAboutActor(actorId: Int, callback: NetworkCallback<Actor>)

    fun getActorImages(actorId: Int, callback: NetworkCallback<ActorImages>)

    fun getActorsSquad(movieId: Int, callback: NetworkCallback<MovieActorSquad>)
}