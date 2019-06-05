package com.example.android.movie.mvp.fullfilms

import com.example.android.database.model.Film
import com.example.android.movie.mvp.base.ILoadingView

interface IFullFilmsView: ILoadingView {

    fun onDownloadResult(result: List<Film>)

    fun onDownloadError(throwable: Throwable)
}