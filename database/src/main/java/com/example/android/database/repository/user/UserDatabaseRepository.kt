package com.example.android.database.repository.user

import com.example.android.database.DatabaseCallback
import com.example.android.database.model.User

interface UserDatabaseRepository{

    fun addUser(user: User, databaseCallback: DatabaseCallback<User>)

    fun updateUser(user: User)

    fun deleteUser(user: User)

    fun findUser(email: String, password: String, databaseCallback: DatabaseCallback<User>)
}