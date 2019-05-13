package com.example.android.network.repository.movies

interface MoviesCallback<T> {

    fun onSuccess(result: T)

    fun onError(throwable: Throwable)
}