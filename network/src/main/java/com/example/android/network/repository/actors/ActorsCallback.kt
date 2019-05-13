package com.example.android.network.repository.actors

interface ActorsCallback<T>{

    fun onSuccess(result: T)

    fun onError(throwable: Throwable)
}