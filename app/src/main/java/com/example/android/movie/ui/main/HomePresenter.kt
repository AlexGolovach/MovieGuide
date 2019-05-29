package com.example.android.movie.ui.main

import com.example.android.database.Callback
import com.example.android.database.Injector
import com.example.android.database.model.User
import com.example.android.movie.mvp.home.IHomePresenter
import com.example.android.movie.mvp.home.IHomeView
import com.example.android.movie.ui.utils.AccountOperation

class HomePresenter(private var iHomeView: IHomeView?) : IHomePresenter {

    override fun closeAccount() {
        Injector.getUserRepositoryImpl().logout(object : Callback<User> {
            override fun onSuccess(result: User) {
                iHomeView?.closeAccount()
                AccountOperation.deleteAccountInformation()
            }

            override fun onError(throwable: Throwable) {
                iHomeView?.closeAccountError()
            }
        })
    }

    override fun onDestroy() {
        iHomeView = null
    }

}