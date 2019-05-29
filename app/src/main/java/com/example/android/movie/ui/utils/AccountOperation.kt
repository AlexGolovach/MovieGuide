package com.example.android.movie.ui.utils

import android.content.Context
import com.example.android.database.App
import com.example.android.database.model.User
import com.google.gson.Gson

object AccountOperation {

    private const val APP_PREFERENCES = "ACCOUNT"
    private const val USER = "USER"

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
        editor?.clear()
        editor?.apply()
    }

    fun createAccount(user: User) {
        val account = gson.toJson(user)

        editor?.putString(USER, account)
        editor?.apply()
    }
}