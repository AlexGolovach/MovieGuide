package com.example.android.movie.ui.utils

import android.content.Context
import com.example.android.movie.App
import com.example.android.database.model.User
import com.google.gson.Gson

object AccountOperation {

    private const val APP_PREFERENCES = "ACCOUNT"
    private const val USER = "USER"
    private const val SAVE_ACC = "SAVE_ACCOUNT"

    private val sharedPreferences =
        App.get()?.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    private val editor = sharedPreferences?.edit()

    private val gson = Gson()

    fun getAccount(): User {
        val user = sharedPreferences?.getString(USER, "")

        return gson.fromJson(user, User::class.java)
    }

    fun deleteAccountInformation() {
        editor?.remove(USER)
        editor?.remove(SAVE_ACC)
        editor?.clear()
        editor?.apply()
    }

    fun createAccount(user: User, saveAcc: Boolean) {
        val account = gson.toJson(user)

        editor?.putBoolean(SAVE_ACC, saveAcc)
        editor?.putString(USER, account)
        editor?.apply()
    }

    fun isAccountSaved(): Boolean{

        return sharedPreferences?.getBoolean(SAVE_ACC,false)?:false    }
}