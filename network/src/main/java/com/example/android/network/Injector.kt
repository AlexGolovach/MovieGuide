package com.example.android.network

import com.example.android.network.repository.actors.ActorsRepository
import com.example.android.network.repository.actors.ActorsRepositoryImpl
import com.example.android.network.repository.movies.MoviesRepository
import com.example.android.network.repository.movies.MoviesRepositoryImpl
import com.example.android.network.repository.videos.VideosRepository
import com.example.android.network.repository.videos.VideosRepositoryImpl

class Injector private constructor() {

    private val moviesRepositoryImpl: MoviesRepositoryImpl =
        MoviesRepositoryImpl()
    private val actorsRepositoryImpl: ActorsRepositoryImpl = ActorsRepositoryImpl()
    private val videosRepositoryImpl: VideosRepositoryImpl = VideosRepositoryImpl()

    companion object {

        @Volatile
        private var instance: Injector? = null

        fun getInstance(): Injector? {
            if (instance == null) {
                synchronized(Injector::class.java) {
                    if (instance == null) {
                        instance = Injector()
                    }
                }
            }

            return instance
        }

        fun getMoviesRepositoryImpl(): MoviesRepository {
            return getInstance()!!.moviesRepositoryImpl
        }

        fun getActorsRepositoryImpl(): ActorsRepository {
            return getInstance()!!.actorsRepositoryImpl
        }

        fun getVideosRepositoryImpl(): VideosRepository {
            return getInstance()!!.videosRepositoryImpl
        }
    }
}