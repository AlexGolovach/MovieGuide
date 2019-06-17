package com.example.android.movie.mvp.favorite

import com.example.android.database.model.Favorite
import com.example.android.movie.mvp.base.ILoadingView

interface IFavoriteView: ILoadingView {

    fun onDownloadFavorites(result: ArrayList<Favorite>)

    fun onDownloadError(error: String)
}