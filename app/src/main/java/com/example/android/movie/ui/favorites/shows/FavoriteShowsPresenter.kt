package com.example.android.movie.ui.favorites.shows

import com.example.android.database.Callback
import com.example.android.database.Injector
import com.example.android.database.model.FavoriteShows
import com.example.android.movie.mvp.favoriteshows.IFavoriteShowsPresenter
import com.example.android.movie.mvp.favoriteshows.IFavoriteShowsView

class FavoriteShowsPresenter(private var iFavoritesView: IFavoriteShowsView?) : IFavoriteShowsPresenter {

    override fun downloadMyFavoriteShows(userId: String) {
        iFavoritesView?.showLoading()

        Injector.getFavoriteShowsRepositoryImpl().downloadMyFavoriteShow(userId, object :
            Callback<ArrayList<FavoriteShows>> {
                override fun onSuccess(result: ArrayList<FavoriteShows>) {
                    iFavoritesView?.onDownloadFavoriteShows(result)
                    iFavoritesView?.hideLoading()
                }

                override fun onError(throwable: Throwable) {
                    iFavoritesView?.onDownloadFavoritesError(throwable)
                    iFavoritesView?.hideLoading()
                }
            })
    }

    override fun deleteShow(show: FavoriteShows) {
        Injector.getFavoriteShowsRepositoryImpl().deleteShow(show, object : Callback<String> {
            override fun onSuccess(result: String) {
                iFavoritesView?.deleteSuccess(result)
            }

            override fun onError(throwable: Throwable) {
                iFavoritesView?.deleteError(throwable)
            }
        })

    }

    override fun onDestroy() {
        iFavoritesView = null
    }

}