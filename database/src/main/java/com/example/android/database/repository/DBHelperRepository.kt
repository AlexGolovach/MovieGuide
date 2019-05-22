package com.example.android.database.repository

import com.example.android.database.Callback
import com.example.android.database.model.User

interface DBHelperRepository{

    fun addUser(user: User, callback: Callback<User>)

    fun updateUser(user: User, callback: Callback<Int>)

    fun deleteUser(user: User)

    fun findUser(email: String, password: String, callback: Callback<User>)
}