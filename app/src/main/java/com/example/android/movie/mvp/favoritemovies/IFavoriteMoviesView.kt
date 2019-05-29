package com.example.android.movie.mvp.favoritemovies

import com.example.android.database.model.FavoriteMovies
import com.example.android.movie.mvp.base.ILoadingView

interface IFavoriteMoviesView: ILoadingView {

    fun onDownloadFavorites(result: ArrayList<FavoriteMovies>)

    fun deleteSuccess(success: String)

    fun deleteError(error: Throwable)

    fun onDownloadFavoritesError(throwable: Throwable)
}