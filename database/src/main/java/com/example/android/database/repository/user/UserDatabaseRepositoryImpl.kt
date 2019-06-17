package com.example.android.database.repository.user

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import com.example.android.database.*
import com.example.android.database.model.User

internal class UserDatabaseRepositoryImpl(context: Context) : BaseSQLiteOpenHelper(context),
    UserDatabaseRepository {

    override fun addUser(user: User, databaseCallback: DatabaseCallback<User>) {

        val cursor = db.rawQuery(
            "SELECT * FROM $USER_TABLE WHERE $LOGIN=? OR $EMAIL=?",
            arrayOf(user.login, user.email)
        )
        cursor.use {
            if (cursor.count == 0) {

                val values = ContentValues()

                values.put(ID, user.id)
                values.put(LOGIN, user.login)
                values.put(EMAIL, user.email)
                values.put(PASSWORD, user.password)

                val result = db.insert(USER_TABLE, null, values)

                if (result == (-1).toLong()) {
                    databaseCallback.onError(NO_ITEMS)
                } else {
                    databaseCallback.onSuccess(user)
                }
            } else {
                databaseCallback.onError(LOGIN_OR_EMAIL_EXIST)
            }

            BaseSQLiteOpenHelper::close
        }
    }

    override fun findUser(
        email: String,
        password: String,
        databaseCallback: DatabaseCallback<User>
    ) {
        val cursor = db.rawQuery(
            "SELECT * FROM $USER_TABLE WHERE $EMAIL=? AND $PASSWORD=?",
            arrayOf(email, password)
        )

        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val userId = it.getInt(it.getColumnIndex(ID))
                    val userLogin = it.getString(it.getColumnIndex(LOGIN))
                    val userEmail = it.getString(it.getColumnIndex(EMAIL))
                    val userPassword = it.getString(it.getColumnIndex(PASSWORD))

                    databaseCallback.onSuccess(User(userId, userLogin, userEmail, userPassword))
                } while (it.moveToNext())
            } else {
                databaseCallback.onError(EMAIL_PASSWORD_NOT_CORRECT)
            }

            BaseSQLiteOpenHelper::close
        }
    }

    override fun updateUser(user: User) {
        val values = ContentValues()

        values.put(ID, user.id)
        values.put(LOGIN, user.login)
        values.put(EMAIL, user.email)
        values.put(PASSWORD, user.password)

        db.update(
            USER_TABLE,
            values,
            "$ID=?",
            arrayOf(user.id.toString())
        )
    }

    override fun deleteUser(user: User) {
        db.delete(USER_TABLE, "$ID=?", arrayOf(user.id.toString()))
    }
}