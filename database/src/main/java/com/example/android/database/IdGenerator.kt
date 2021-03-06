package com.example.android.database

import java.util.*

object IdGenerator {

    fun generateId(): Int {
        val uuid = UUID.randomUUID()
        var str = uuid.toString()
        val uid = str.hashCode()
        val filterStr = uid.toString()
        str = filterStr.replace("-".toRegex(), "")

        return str.toInt()
    }
}
