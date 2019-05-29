package com.example.android.network.repository.actors

import com.example.android.network.models.actor.Actor
import com.example.android.network.models.actor.ActorImages
import com.example.android.network.models.moviesquad.MovieActorSquad
import com.example.android.network.models.showsquad.ActorShowSquad

interface ActorsRepository{

    fun getInformationAboutActor(actorId: Int, callback: ActorsCallback<Actor>)

    fun getActorImages(actorId: Int, callback: ActorsCallback<ActorImages>)

    fun getMovieActorsSquad(movieId: Int, callback: ActorsCallback<MovieActorSquad>)

    fun getShowActorSquad(showId: Int, callback: ActorsCallback<ActorShowSquad>)
}