package com.example.android.movie.ui.fullserialsfilms.fullfilms

import com.example.android.database.Callback
import com.example.android.database.Injector
import com.example.android.database.model.Film
import com.example.android.movie.mvp.fullfilms.IFullFilmsPresenter
import com.example.android.movie.mvp.fullfilms.IFullFilmsView

class FullFilmsPresenter(private var iFullFilmsView: IFullFilmsView?) : IFullFilmsPresenter {

    override fun onDownloadFullFilms() {
        iFullFilmsView?.showLoading()

        Injector.getFullSerialsFilmsRepositoryImpl().loadFilms(object : Callback<List<Film>> {
            override fun onSuccess(result: List<Film>) {
                iFullFilmsView?.onDownloadResult(result)
                iFullFilmsView?.hideLoading()
            }

            override fun onError(throwable: Throwable) {
                iFullFilmsView?.onDownloadError(throwable)
                iFullFilmsView?.hideLoading()
            }
        })
    }

    override fun onDestroy() {
        iFullFilmsView = null
    }

}