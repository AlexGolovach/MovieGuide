package com.example.android.movie.ui.widget.dialog

import ImageLoader
import android.graphics.Bitmap
import com.example.android.imageloader.Callback
import com.example.android.movie.mvp.dialogimage.IDialogImagePresenter
import com.example.android.movie.mvp.dialogimage.IDialogImageView

class DialogImagePresenter(private var iDialogImageView: IDialogImageView?) :
    IDialogImagePresenter {

    override fun onDownloadImage(imageUrl: String) {
        ImageLoader.getInstance()?.load(imageUrl, object : Callback {
            override fun onSuccess(url: String, bitmap: Bitmap) {
                iDialogImageView?.onDownloadActorImage(bitmap)
            }

            override fun onError(url: String, throwable: Throwable) {

            }
        })
    }

    override fun onDestroy() {
        iDialogImageView = null
    }
}