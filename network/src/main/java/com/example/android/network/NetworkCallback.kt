package com.example.android.network

interface NetworkCallback<T> {

    fun onSuccess(result: T)

    fun onError(error: String)
}