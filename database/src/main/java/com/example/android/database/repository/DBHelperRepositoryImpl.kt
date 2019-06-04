package com.example.android.database.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.android.database.Callback
import com.example.android.database.model.User

class DBHelperRepositoryImpl(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null,
    DATABASE_VERSION
), DBHelperRepository {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "USERS.db"

        private const val TABLE_NAME = "User"
        private const val ID = "Id"
        private const val LOGIN = "Login"
        private const val EMAIL = "Email"
        private const val PASSWORD = "Password"
    }

    private val db = this.writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        //TODO create table if table is not exist
        val table =
            ("CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY,$LOGIN TEXT,$EMAIL TEXT,$PASSWORD TEXT)")
        db?.execSQL(table)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    override fun addUser(user: User, callback: Callback<User>) {

        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE $LOGIN=? OR $EMAIL=?",
            arrayOf(user.login, user.email)
        )
//        cursor.use {
//
//        }
        if (cursor.count == 0) {
            cursor.close()

            val values = ContentValues()

            values.put(ID, user.id)
            values.put(LOGIN, user.login)
            values.put(EMAIL, user.email)
            values.put(PASSWORD, user.password)

            val result = db.insert(TABLE_NAME, null, values)

            if (result == (-1).toLong()) {
                callback.onError("Error")
            } else {
                callback.onSuccess(user)
            }
        } else {
            callback.onError("Login or email exist. Please write new information")
        }

        db.close()
    }

    override fun findUser(email: String, password: String, callback: Callback<User>) {
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE $EMAIL=? AND $PASSWORD=?",
            arrayOf(email, password)
        )

        if (cursor.moveToFirst()) {
            do {
                val userId = cursor.getInt(cursor.getColumnIndex(ID))
                val userLogin = cursor.getString(cursor.getColumnIndex(LOGIN))
                val userEmail = cursor.getString(cursor.getColumnIndex(EMAIL))
                val userPassword = cursor.getString(cursor.getColumnIndex(PASSWORD))

                callback.onSuccess(User(userId, userLogin, userEmail, userPassword))
            }while (cursor.moveToNext())
        } else {
            callback.onError("Email or password not correct")
        }

        cursor.close()
    }

    override fun updateUser(user: User, callback: Callback<Int>) {
        val values = ContentValues()

        values.put(ID, user.id)
        values.put(LOGIN, user.login)
        values.put(EMAIL, user.email)
        values.put(PASSWORD, user.password)

        callback.onSuccess(db.update(TABLE_NAME, values, "$ID=?", arrayOf(user.id.toString())))
    }

    override fun deleteUser(user: User) {
        db.delete(TABLE_NAME, "$ID=?", arrayOf(user.id.toString()))
        db.close()
    }
}