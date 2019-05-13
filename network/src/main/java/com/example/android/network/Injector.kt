package com.example.android.network

import com.example.android.network.repository.actors.ActorsRepository
import com.example.android.network.repository.actors.ActorsRepositoryImpl
import com.example.android.network.repository.movies.MoviesRepository
import com.example.android.network.repository.movies.MoviesRepositoryImpl

class Injector private constructor() {

    private val moviesRepositoryImpl: MoviesRepositoryImpl =
        MoviesRepositoryImpl()
    private val actorsRepositoryImpl: ActorsRepositoryImpl = ActorsRepositoryImpl()

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

        fun getActorsRepositoryImpl(): ActorsRepository{
            return getInstance()!!.actorsRepositoryImpl
        }
    }
}