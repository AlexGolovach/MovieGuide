package com.example.android.movie.ui.register.login

import com.example.android.database.Callback
import com.example.android.database.Injector
import com.example.android.database.model.User
import com.example.android.movie.mvp.login.ILoginPresenter
import com.example.android.movie.mvp.login.ILoginView
import com.example.android.movie.ui.utils.AccountOperation

class LoginPresenter(private var iLoginView: ILoginView?) : ILoginPresenter {
    override fun findUser(email: String, password: String) {
        Injector.getUserRepositoryImpl().login(email, password, object : Callback<User> {
            override fun onSuccess(result: User) {
                AccountOperation.createAccount(result)

                iLoginView?.entrySuccess(result)
            }

            override fun onError(throwable: Throwable) {
                iLoginView?.entryError(throwable)
            }
        })
    }

    override fun onDestroy() {
        iLoginView = null
    }

}