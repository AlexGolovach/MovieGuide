package com.example.android.movie.mvp.dialogimage

interface IDialogImagePresenter {

    fun onDownloadImage(imageUrl: String)

    fun onDestroy()
}