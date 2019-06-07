package com.example.android.movie.ui.main.topserials

import com.example.android.movie.mvp.topserials.ITopSerialsPresenter
import com.example.android.movie.mvp.topserials.ITopSerialsView
import com.example.android.network.Injector
import com.example.android.network.models.serial.SerialsList
import com.example.android.network.repository.serials.SerialsCallback

class TopSerialsPresenter(private var topSerialsView: ITopSerialsView?) :
    ITopSerialsPresenter {

    override fun onDownloadSerials() {
        topSerialsView?.showLoading()

        Injector.getSerialsRepositoryImpl().getPopularSerials(object :
            SerialsCallback<SerialsList> {
            override fun onSuccess(result: SerialsList) {
                topSerialsView?.onDownloadResult(result)
                topSerialsView?.hideLoading()
            }

            override fun onError(throwable: Throwable) {
                topSerialsView?.onDownloadError(throwable)
                topSerialsView?.hideLoading()
            }
        })
    }

    override fun onSearchSerials(query: String?) {

        val userInput = query?.toLowerCase()

        query?.let {
            if (it.length >= 3) {
                Injector.getSerialsRepositoryImpl()
                    .getSearchResultSerials(query, object :
                        SerialsCallback<SerialsList> {
                        override fun onSuccess(result: SerialsList) {
                            for (movie in result.results) {
                                if (movie.title?.toLowerCase()!!.contains(userInput.toString())) {
                                    topSerialsView?.onSearchResult(result)
                                }
                            }
                        }

                        override fun onError(throwable: Throwable) {
                            topSerialsView?.onDownloadError(throwable)
                        }
                    })
            }
        }
    }

    override fun onDestroy() {
        topSerialsView = null
    }
}