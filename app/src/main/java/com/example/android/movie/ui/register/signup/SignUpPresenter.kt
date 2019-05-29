package com.example.android.movie.ui.register.signup

import com.example.android.database.Callback
import com.example.android.database.Injector
import com.example.android.database.model.User
import com.example.android.movie.mvp.signup.ISignUpPresenter
import com.example.android.movie.mvp.signup.ISignUpView
import com.example.android.movie.ui.utils.AccountOperation

class SignUpPresenter(private var iSignUpView: ISignUpView?) : ISignUpPresenter {

    override fun createUser(username: String, email: String, password: String) {

        Injector.getUserRepositoryImpl().signUp(username, email, password, object : Callback<User> {
            override fun onSuccess(result: User) {
                AccountOperation.createAccount(result)

                iSignUpView?.createUserSuccess(result)
            }

            override fun onError(throwable: Throwable) {
                iSignUpView?.createUserError(throwable)
            }
        })
    }

    override fun onDestroy() {
        iSignUpView = null
    }
}