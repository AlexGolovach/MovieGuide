package com.example.android.movie.ui.favorites.movies

import com.example.android.database.Callback
import com.example.android.database.Injector
import com.example.android.database.model.FavoriteMovies
import com.example.android.movie.mvp.favoritemovies.IFavoriteMoviesPresenter
import com.example.android.movie.mvp.favoritemovies.IFavoriteMoviesView

class FavoriteMoviesPresenter(private var iFavoritesView: IFavoriteMoviesView?) :
    IFavoriteMoviesPresenter {

    override fun downloadMyFavorites(userId: String) {
        iFavoritesView?.showLoading()

        Injector.getFavoriteMoviesRepositoryImpl()
            .downloadMyFavoriteMovies(userId, object : Callback<ArrayList<FavoriteMovies>> {
                override fun onSuccess(result: ArrayList<FavoriteMovies>) {
                    iFavoritesView?.onDownloadFavorites(result)
                    iFavoritesView?.hideLoading()
                }

                override fun onError(throwable: Throwable) {
                    iFavoritesView?.onDownloadFavoritesError(throwable)
                    iFavoritesView?.hideLoading()
                }
            })
    }

    override fun deleteMovie(movie: FavoriteMovies) {
        Injector.getFavoriteMoviesRepositoryImpl().deleteMovie(movie, object : Callback<String> {
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