package com.example.android.network.repository.actors

import com.example.android.network.models.actor.Actor
import com.example.android.network.models.movie.MovieActorSquad

interface ActorsRepository{

    fun getInformationAboutActor(actorId: Long, callback: ActorsCallback<Actor>)

    fun getActorImages(actorId: Long, callback: ActorsCallback<List<String>>)

    fun getActorsSquad(movieId: Long, callback: ActorsCallback<List<MovieActorSquad>>)
}