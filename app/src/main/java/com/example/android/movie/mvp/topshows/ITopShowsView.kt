package com.example.android.movie.mvp.topshows

import com.example.android.movie.mvp.base.ILoadingView
import com.example.android.network.models.shows.ShowsList

interface ITopShowsView: ILoadingView {

    fun onDownloadResult(shows: ShowsList)

    fun onDownloadError(throwable: Throwable)
}