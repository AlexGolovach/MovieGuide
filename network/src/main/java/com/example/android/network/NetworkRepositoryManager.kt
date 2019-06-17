package com.example.android.network

import com.example.android.network.repository.actors.ActorsRepository
import com.example.android.network.repository.actors.ActorsRepositoryImpl
import com.example.android.network.repository.movies.MoviesRepository
import com.example.android.network.repository.movies.MoviesRepositoryImpl
import com.example.android.network.repository.serials.SerialsRepository
import com.example.android.network.repository.serials.SerialsRepositoryImpl
import com.example.android.network.repository.videos.VideosRepository
import com.example.android.network.repository.videos.VideosRepositoryImpl

object NetworkRepositoryManager {

    fun getMoviesRepositoryImpl(): MoviesRepository {
        return MoviesRepositoryImpl()
    }

    fun getActorsRepositoryImpl(): ActorsRepository {
        return ActorsRepositoryImpl()
    }

    fun getVideosRepositoryImpl(): VideosRepository {
        return VideosRepositoryImpl()
    }

    fun getSerialsRepositoryImpl(): SerialsRepository {
        return SerialsRepositoryImpl()
    }
}