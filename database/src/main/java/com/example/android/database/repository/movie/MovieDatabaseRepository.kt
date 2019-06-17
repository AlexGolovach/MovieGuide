package com.example.android.database.repository.movie

import com.example.android.database.DatabaseCallback
import com.example.android.database.model.ActorSquad
import com.example.android.database.model.Details
import com.example.android.database.model.Movie
import com.example.android.database.model.Video

interface MovieDatabaseRepository {

    fun addInformation(details: Details)

    fun loadInformation(id: Int, databaseCallback: DatabaseCallback<Details>)

    fun addActorSquad(movieId: Int, actorSquad: List<ActorSquad>)

    fun loadActorSquad(movieId: Int, databaseCallback: DatabaseCallback<List<ActorSquad>>)

    fun addRecommendedMovies(movieId: Int, recommendMovie: List<Movie>)

    fun loadRecommendedMovies(movieId: Int, databaseCallback: DatabaseCallback<List<Movie>>)

    fun addVideo(movieId: Int, video: List<Video>)

    fun loadVideo(movieId: Int, databaseCallback: DatabaseCallback<List<Video>>)

    fun checkMovieInFavorite(userId: Int, filmOrSerial: Int, databaseCallback: DatabaseCallback<Boolean>)
}