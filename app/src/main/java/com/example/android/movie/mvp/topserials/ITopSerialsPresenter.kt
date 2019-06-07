package com.example.android.movie.mvp.topserials

interface ITopSerialsPresenter {

    fun onDownloadSerials()

    fun onSearchSerials(query: String?)

    fun onDestroy()
}