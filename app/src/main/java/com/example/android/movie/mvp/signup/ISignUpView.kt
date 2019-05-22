package com.example.android.movie.mvp.signup

interface ISignUpView{

    fun createUserSuccess(success: String)

    fun createUserError(error: String)
}