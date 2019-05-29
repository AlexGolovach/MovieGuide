package com.example.android.movie.mvp.favoriteshows

import com.example.android.database.model.FavoriteShows
import com.example.android.movie.mvp.base.ILoadingView

interface IFavoriteShowsView :ILoadingView{

    fun onDownloadFavoriteShows(result: ArrayList<FavoriteShows>)

    fun deleteSuccess(success: String)

    fun deleteError(error: Throwable)

    fun onDownloadFavoritesError(throwable: Throwable)
}