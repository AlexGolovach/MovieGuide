package com.example.android.database

import android.content.Context
import android.preference.PreferenceManager
import com.example.android.database.repository.favoritemovies.FavoriteMoviesRepository
import com.example.android.database.repository.favoritemovies.FavoriteMoviesRepositoryImpl
import com.example.android.database.repository.favoriteshows.FavoriteShowsRepository
import com.example.android.database.repository.favoriteshows.FavoriteShowsRepositoryImpl
import com.example.android.database.repository.reviews.ReviewsRepository
import com.example.android.database.repository.reviews.ReviewsRepositoryImpl
import com.example.android.database.repository.user.UserRepository
import com.example.android.database.repository.user.UserRepositoryImpl

class Injector private constructor(context: Context) {

    private val userRepositoryImpl: UserRepositoryImpl =
        UserRepositoryImpl(
            PreferenceManager.getDefaultSharedPreferences(
                context
            )
        )
    private val favoritesMovieRepositoryImpl: FavoriteMoviesRepositoryImpl =
        FavoriteMoviesRepositoryImpl()
    private val favoriteShowsRepositoryImpl: FavoriteShowsRepositoryImpl =
        FavoriteShowsRepositoryImpl()
    private val reviewsRepositoryImpl: ReviewsRepositoryImpl = ReviewsRepositoryImpl()

    companion object {

        @Volatile
        private var instance: Injector? = null

        private fun getInstance(): Injector? {
            if (instance == null) {
                synchronized(Injector::class.java) {
                    if (instance == null) {
                        instance = Injector(App.get()!!)
                    }
                }
            }

            return instance
        }

        fun getUserRepositoryImpl(): UserRepository {
            return getInstance()!!.userRepositoryImpl
        }

        fun getFavoriteMoviesRepositoryImpl(): FavoriteMoviesRepository {
            return getInstance()!!.favoritesMovieRepositoryImpl
        }

        fun getFavoriteShowsRepositoryImpl(): FavoriteShowsRepository {
            return getInstance()!!.favoriteShowsRepositoryImpl
        }

        fun getReviewsRepositoryImpl(): ReviewsRepository {
            return getInstance()!!.reviewsRepositoryImpl
        }
    }
}