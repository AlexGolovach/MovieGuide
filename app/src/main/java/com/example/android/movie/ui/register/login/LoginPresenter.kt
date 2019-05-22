package com.example.android.movie.ui.register.login

import com.example.android.database.Callback
import com.example.android.database.Injector
import com.example.android.database.model.User
import com.example.android.movie.mvp.login.ILoginPresenter
import com.example.android.movie.mvp.login.ILoginView
import com.example.android.movie.ui.utils.AccountOperation.createAccount

class LoginPresenter(private var iLoginView: ILoginView?): ILoginPresenter{
    override fun findUser(email: String, password: String, saveAcc: Boolean) {
        Injector.getDBRepositoryImpl().findUser(email,password,object: Callback<User>{
            override fun onSuccess(result: User) {
                createAccount(result, saveAcc)
                iLoginView?.findUserSuccess("Success")
            }

            override fun onError(error: String) {
                iLoginView?.findUserError("Error")
            }
        })
    }

    override fun onDestroy() {
        iLoginView = null
    }

}