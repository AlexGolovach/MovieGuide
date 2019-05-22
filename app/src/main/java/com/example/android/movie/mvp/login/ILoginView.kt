package com.example.android.movie.mvp.login

interface ILoginView{

    fun findUserSuccess(success: String)

    fun findUserError(error: String)
}