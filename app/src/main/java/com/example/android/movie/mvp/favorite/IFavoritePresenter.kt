package com.example.android.movie.mvp.favorite

interface IFavoritePresenter {

    fun loadFavorites(userId: Int)

    fun deleteFromFavorite(userId: Int, movieOrSerialId: Int)

    fun onDestroy()
}