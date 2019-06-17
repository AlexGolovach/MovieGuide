package com.example.android.movie.ui.main.favorite

import com.example.android.database.DatabaseCallback
import com.example.android.database.DatabaseRepositoryManager
import com.example.android.database.model.Favorite
import com.example.android.movie.App
import com.example.android.movie.mvp.favorite.IFavoritePresenter
import com.example.android.movie.mvp.favorite.IFavoriteView

class FavoritePresenter(private var iFavoriteView: IFavoriteView?) : IFavoritePresenter {

    override fun loadFavorites(userId: Int) {
        iFavoriteView?.showLoading()

        App.get()?.let {
            DatabaseRepositoryManager.getFavoriteDatabaseRepositoryImpl(it)
                .loadFavorites(userId, object : DatabaseCallback<ArrayList<Favorite>> {
                    override fun onSuccess(result: ArrayList<Favorite>) {
                        iFavoriteView?.onDownloadFavorites(result)
                        iFavoriteView?.hideLoading()
                    }

                    override fun onError(error: String) {
                        iFavoriteView?.onDownloadError(error)
                        iFavoriteView?.hideLoading()
                    }
                })
        }
    }

    override fun deleteFromFavorite(userId: Int, movieOrSerialId: Int) {
        App.get()?.let {
            DatabaseRepositoryManager.getFavoriteDatabaseRepositoryImpl(it)
                .deleteFromFavorites(userId, movieOrSerialId)
        }
    }

    override fun onDestroy() {
        iFavoriteView = null
    }
}