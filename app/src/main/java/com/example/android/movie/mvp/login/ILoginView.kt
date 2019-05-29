package com.example.android.movie.mvp.login

import com.example.android.database.model.User

interface ILoginView {

    fun entrySuccess(user: User)

    fun entryError(error: Throwable)
}