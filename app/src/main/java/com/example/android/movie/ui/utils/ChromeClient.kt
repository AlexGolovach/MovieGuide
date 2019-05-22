package com.example.android.movie.ui.utils

import android.view.View
import android.webkit.WebChromeClient

class ChromeClient : WebChromeClient() {

    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
        super.onShowCustomView(view, callback)

        callback?.onCustomViewHidden()
    }

    override fun onHideCustomView() {
        super.onHideCustomView()
    }
}