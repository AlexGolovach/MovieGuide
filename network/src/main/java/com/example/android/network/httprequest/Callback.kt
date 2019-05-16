package com.example.android.network.httprequest

interface Callback {

    fun onSuccess(json: String)

    fun onError(throwable: Throwable)
}