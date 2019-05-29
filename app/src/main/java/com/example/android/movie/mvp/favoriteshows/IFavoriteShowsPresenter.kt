package com.example.android.movie.mvp.favoriteshows

import com.example.android.database.model.FavoriteShows

interface IFavoriteShowsPresenter {

    fun downloadMyFavoriteShows(userId: String)

    fun deleteShow(show: FavoriteShows)

    fun onDestroy()
}