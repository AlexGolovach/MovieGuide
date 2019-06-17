package com.example.android.database

interface DatabaseCallback <T>{

    fun onSuccess(result: T)

    fun onError(error: String)
}