package com.example.android.database.repository

import com.example.android.database.Callback
import com.example.android.database.model.ActorSquad
import com.example.android.database.model.Information
import com.example.android.database.model.Movie
import com.example.android.database.model.User

interface DBHelperRepository{

    fun addUser(user: User, callback: Callback<User>)

    fun updateUser(user: User, callback: Callback<Int>)

    fun deleteUser(user: User)

    fun findUser(email: String, password: String, callback: Callback<User>)

    fun addInformation(information: Information)

    fun loadInformation(id: Int, callback: Callback<Information>)

    fun addActorSquad(movieId: Int, actorSquad: List<ActorSquad>)

    fun loadActorSquad(movieId: Int, callback: Callback<List<ActorSquad>>)

    fun addRecommendedMovies(movieId: Int, recommendMovie: List<Movie>)

    fun loadRecommendedMovies(movieId: Int, callback: Callback<List<Movie>>)

    fun addVideo(movieId: Int, video: List<String>)

    fun loadVideo(movieId: Int, callback: Callback<List<String>>)
}