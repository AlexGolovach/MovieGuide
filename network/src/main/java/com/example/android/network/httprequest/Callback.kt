package com.example.android.network.httprequest

interface Callback {

    fun onSuccess(result: String)

    fun onError(throwable: Throwable)
}