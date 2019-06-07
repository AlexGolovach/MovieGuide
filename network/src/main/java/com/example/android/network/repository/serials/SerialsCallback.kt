package com.example.android.network.repository.serials

interface SerialsCallback<T>{

    fun onSuccess(result: T)

    fun onError(throwable: Throwable)
}