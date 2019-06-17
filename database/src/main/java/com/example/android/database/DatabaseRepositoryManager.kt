package com.example.android.database

import android.content.Context
import com.example.android.database.repository.actor.ActorDatabaseRepositoryImpl
import com.example.android.database.repository.actor.ActorDatabaseRepository
import com.example.android.database.repository.favorite.FavoriteRepository
import com.example.android.database.repository.favorite.FavoriteRepositoryImpl
import com.example.android.database.repository.movie.MovieDatabaseRepository
import com.example.android.database.repository.movie.MovieDatabaseRepositoryImpl
import com.example.android.database.repository.user.UserDatabaseRepository
import com.example.android.database.repository.user.UserDatabaseRepositoryImpl

object DatabaseRepositoryManager {

    fun getUserDatabaseRepositoryImpl(context: Context): UserDatabaseRepository {
        return UserDatabaseRepositoryImpl(context)
    }

    fun getMovieDatabaseRepositoryImpl(context: Context): MovieDatabaseRepository {
        return MovieDatabaseRepositoryImpl(context)
    }

    fun getActorDatabaseRepositoryImpl(context: Context): ActorDatabaseRepository {
        return ActorDatabaseRepositoryImpl(context)
    }

    fun getFavoriteDatabaseRepositoryImpl(context: Context): FavoriteRepository {
        return FavoriteRepositoryImpl(context)
    }
}