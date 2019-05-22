package com.example.android.database

import android.content.Context
import com.example.android.database.repository.DBHelperRepository
import com.example.android.database.repository.DBHelperRepositoryImpl

class Injector private constructor(context: Context){

    private val dbHelperRepositoryImpl = DBHelperRepositoryImpl(context)

    companion object {

        @Volatile
        private var instance:Injector? = null

        fun getInstance(): Injector?{
            if (instance == null){
                synchronized(Injector::class.java){
                    if (instance == null){
                        instance = Injector(App.get()!!)
                    }
                }
            }

            return instance
        }

        fun getDBRepositoryImpl(): DBHelperRepository{
            return getInstance()!!.dbHelperRepositoryImpl
        }
    }
}