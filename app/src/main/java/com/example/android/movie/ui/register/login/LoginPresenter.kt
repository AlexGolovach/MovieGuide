package com.example.android.movie.ui.register.login

import com.example.android.database.DatabaseCallback
import com.example.android.database.DatabaseRepositoryManager
import com.example.android.database.model.User
import com.example.android.movie.App
import com.example.android.movie.mvp.login.ILoginPresenter
import com.example.android.movie.mvp.login.ILoginView
import com.example.android.movie.ui.utils.AccountOperation.createAccount

class LoginPresenter(private var iLoginView: ILoginView?): ILoginPresenter{
    override fun findUser(email: String, password: String, saveAcc: Boolean) {
        App.get()?.let {
            DatabaseRepositoryManager.getUserDatabaseRepositoryImpl(it).findUser(email,password,object: DatabaseCallback<User>{
                override fun onSuccess(result: User) {
                    createAccount(result, saveAcc)

                    iLoginView?.findUserSuccess()
                }

                override fun onError(error: String) {
                    iLoginView?.findUserError(error)
                }
            })
        }
    }

    override fun onDestroy() {
        iLoginView = null
    }

}