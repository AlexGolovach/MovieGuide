package com.example.android.movie.mvp.signup

import com.example.android.database.model.User

interface ISignUpView{

    fun createUserSuccess(user: User)

    fun createUserError(error: Throwable)
}