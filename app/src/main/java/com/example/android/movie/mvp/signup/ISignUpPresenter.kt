package com.example.android.movie.mvp.signup

interface ISignUpPresenter{

    fun createUser(username: String, email: String, password: String)

    fun onDestroy()
}