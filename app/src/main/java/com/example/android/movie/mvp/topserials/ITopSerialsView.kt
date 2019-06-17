package com.example.android.movie.mvp.topserials

import com.example.android.movie.mvp.base.ILoadingView
import com.example.android.network.models.serial.SerialsList

interface ITopSerialsView: ILoadingView {

    fun onDownloadResult(serials: SerialsList)

    fun onDownloadError(error: String)

    fun onSearchResult(result: SerialsList)
}