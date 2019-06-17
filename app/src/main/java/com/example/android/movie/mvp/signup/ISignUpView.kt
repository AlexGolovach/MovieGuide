package com.example.android.movie.mvp.signup

interface ISignUpView{

    fun createUserSuccess()

    fun createUserError(error: String)
}