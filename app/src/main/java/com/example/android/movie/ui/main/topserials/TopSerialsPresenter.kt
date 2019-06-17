package com.example.android.movie.ui.main.topserials

import android.widget.Toast
import com.example.android.movie.App
import com.example.android.movie.mvp.topserials.ITopSerialsPresenter
import com.example.android.movie.mvp.topserials.ITopSerialsView
import com.example.android.network.NetworkRepositoryManager
import com.example.android.network.NetworkCallback
import com.example.android.network.models.serial.SerialsList

class TopSerialsPresenter(private var topSerialsView: ITopSerialsView?) :
    ITopSerialsPresenter {

    override fun onDownloadSerials(page: Int) {

        NetworkRepositoryManager.getSerialsRepositoryImpl().getPopularSerials(page, object :
            NetworkCallback<SerialsList> {
            override fun onSuccess(result: SerialsList) {
                if (page != result.totalPages) {
                    topSerialsView?.onDownloadResult(result)
                    topSerialsView?.hideLoading()
                } else {
                    Toast.makeText(App.get(), "All items", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(error: String) {
                topSerialsView?.onDownloadError(error)
                topSerialsView?.hideLoading()
            }
        })
    }

    override fun onSearchSerials(query: String?) {

        val userInput = query?.toLowerCase()

        query?.let {
            if (it.length >= 3) {
                NetworkRepositoryManager.getSerialsRepositoryImpl()
                    .getSearchResultSerials(query, object :
                        NetworkCallback<SerialsList> {
                        override fun onSuccess(result: SerialsList) {
                            for (movie in result.results) {
                                if (movie.title?.toLowerCase()!!.contains(userInput.toString())) {
                                    topSerialsView?.onSearchResult(result)
                                }
                            }
                        }

                        override fun onError(error: String) {
                            topSerialsView?.onDownloadError(error)
                        }
                    })
            }
        }
    }

    override fun onDestroy() {
        topSerialsView = null
    }
}