package com.example.android.movie.ui.main.moviedetails.serial

import com.example.android.movie.mvp.serialdetails.ISerialDetailsPresenter
import com.example.android.movie.mvp.serialdetails.ISerialDetailsView
import com.example.android.network.Injector
import com.example.android.network.NetworkCallback
import com.example.android.network.models.SerialDetails
import com.example.android.network.models.VideoList
import com.example.android.network.models.recommendedserials.RecommendSerialsList
import com.example.android.network.models.serialsquad.SerialActorSquad

class SerialDetailsPresenter(private var iSerialDetailsView: ISerialDetailsView?) :
    ISerialDetailsPresenter {

    private var youTubeVideos = mutableListOf<String>()

    override fun onDownloadSerialDetails(serialId: Int) {
        iSerialDetailsView?.showLoading()

        Injector.getSerialsRepositoryImpl()
            .getSerialDetails(serialId, object : NetworkCallback<SerialDetails> {
                override fun onSuccess(result: SerialDetails) {
                    iSerialDetailsView?.onDownloadResultDetails(result)
                }

                override fun onError(throwable: Throwable) {
                    iSerialDetailsView?.onDownloadDetailsError(throwable)
                    iSerialDetailsView?.hideLoading()
                }

            })
    }

    override fun onDownloadActorSquad(serialId: Int) {
        Injector.getActorsRepositoryImpl().getSerialActorSquad(serialId, object :
            NetworkCallback<SerialActorSquad> {
            override fun onSuccess(result: SerialActorSquad) {
                iSerialDetailsView?.onDownloadActorSquad(result)
            }

            override fun onError(throwable: Throwable) {
                iSerialDetailsView?.onDownloadDetailsError(throwable)
                iSerialDetailsView?.hideLoading()
            }
        })
    }

    override fun onDownloadRecommendedSerials(serialId: Int) {
        Injector.getSerialsRepositoryImpl()
            .getRecommendedSerialsForSerial(serialId, object : NetworkCallback<RecommendSerialsList> {
                override fun onSuccess(result: RecommendSerialsList) {
                    iSerialDetailsView?.onDownloadRecommendedSerials(result)
                }

                override fun onError(throwable: Throwable) {
                    iSerialDetailsView?.onDownloadDetailsError(throwable)
                    iSerialDetailsView?.hideLoading()
                }
            })
    }

    override fun onDownloadVideo(serialId: Int) {
        Injector.getVideosRepositoryImpl().getVideosForSerial(serialId, object : NetworkCallback<VideoList> {
            override fun onSuccess(result: VideoList) {
                youTubeVideos.clear()

                for (i in 0 until result.results.size) {
                    youTubeVideos.add("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/${result.results[i].key}\" frameborder=\"0\" allowfullscreen></iframe>")
                }

                iSerialDetailsView?.onDownloadVideo(youTubeVideos)
                iSerialDetailsView?.hideLoading()
            }

            override fun onError(error: Throwable) {
                iSerialDetailsView?.hideLoading()
            }
        })
    }

    override fun onDestroy() {
        iSerialDetailsView = null
    }

}