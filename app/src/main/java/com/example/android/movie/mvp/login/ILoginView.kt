package com.example.android.movie.mvp.login

interface ILoginView{

    fun findUserSuccess()

    fun findUserError(error: String)
}