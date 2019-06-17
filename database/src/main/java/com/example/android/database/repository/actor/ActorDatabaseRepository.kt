package com.example.android.database.repository.actor

import com.example.android.database.DatabaseCallback
import com.example.android.database.model.Actor
import com.example.android.database.model.Movie

interface ActorDatabaseRepository {

    fun addActorInformation(actor: Actor)

    fun loadActorInformation(id: Int, databaseCallback: DatabaseCallback<Actor>)

    fun addActorImages(actorId: Int, image: List<String>)

    fun loadActorImages(id: Int, databaseCallback: DatabaseCallback<List<String>>)

    fun addActorMovies(actorId: Int, movie: List<Movie>)

    fun loadActorMovies(id: Int, databaseCallback: DatabaseCallback<List<Movie>>)
}