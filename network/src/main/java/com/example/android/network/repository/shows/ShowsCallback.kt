package com.example.android.network.repository.shows

interface ShowsCallback<T>{

    fun onSuccess(result: T)

    fun onError(throwable: Throwable)
}