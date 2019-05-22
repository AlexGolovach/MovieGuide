package com.example.android.movie.mvp.login

interface ILoginPresenter{

    fun findUser(email: String, password: String, saveAcc: Boolean)

    fun onDestroy()
}