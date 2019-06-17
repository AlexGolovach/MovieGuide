package com.example.android.movie.mvp.topserials

interface ITopSerialsPresenter {

    fun onDownloadSerials(page: Int = 1)

    fun onSearchSerials(query: String?)

    fun onDestroy()
}