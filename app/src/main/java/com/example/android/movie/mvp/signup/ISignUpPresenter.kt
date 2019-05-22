package com.example.android.movie.mvp.signup

import com.example.android.database.model.User

interface ISignUpPresenter{

    fun createUser(user: User, saveAcc: Boolean)

    fun onDestroy()
}