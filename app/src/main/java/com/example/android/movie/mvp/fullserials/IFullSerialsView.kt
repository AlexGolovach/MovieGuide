package com.example.android.movie.mvp.fullserials

import com.example.android.database.model.Serial
import com.example.android.movie.mvp.base.ILoadingView

interface IFullSerialsView: ILoadingView{

    fun onDownloadResult(result: List<Serial>)

    fun onDownloadError(throwable: Throwable)
}