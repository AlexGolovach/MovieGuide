package com.example.android.movie.ui.main.topshows

import com.example.android.movie.mvp.topshows.ITopShowsPresenter
import com.example.android.movie.mvp.topshows.ITopShowsView
import com.example.android.network.Injector
import com.example.android.network.models.shows.ShowsList
import com.example.android.network.repository.shows.ShowsCallback

class TopShowsPresenter(private var topShowsView: ITopShowsView?) :
    ITopShowsPresenter {

    override fun onDownloadShows() {
        topShowsView?.showLoading()

        Injector.getShowsRepositoryImpl().getPopularShows(object :
            ShowsCallback<ShowsList> {
            override fun onSuccess(result: ShowsList) {
                topShowsView?.onDownloadResult(result)
                topShowsView?.hideLoading()
            }

            override fun onError(throwable: Throwable) {
                topShowsView?.onDownloadError(throwable)
                topShowsView?.hideLoading()
            }
        })
    }

    override fun onDestroy(){
        topShowsView = null
    }
}