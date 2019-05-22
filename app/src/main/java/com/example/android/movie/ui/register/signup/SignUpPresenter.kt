package com.example.android.movie.ui.register.signup

import com.example.android.database.Callback
import com.example.android.database.Injector
import com.example.android.database.model.User
import com.example.android.movie.mvp.signup.ISignUpPresenter
import com.example.android.movie.mvp.signup.ISignUpView
import com.example.android.movie.ui.utils.AccountOperation.createAccount

class SignUpPresenter(private var iSignUpView: ISignUpView?) : ISignUpPresenter {

    override fun createUser(user: User, saveAcc: Boolean) {

        Injector.getDBRepositoryImpl().addUser(user, object : Callback<User> {
            override fun onSuccess(result: User) {
                createAccount(result, saveAcc)
                iSignUpView?.createUserSuccess("Success")
            }

            override fun onError(error: String) {
                iSignUpView?.createUserError(error)
            }
        })
    }

    override fun onDestroy() {
        iSignUpView = null
    }
}