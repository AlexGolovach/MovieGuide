package com.example.android.network

interface Callback {

    fun onSuccess(result: String)

    fun onError(throwable: Throwable)
}