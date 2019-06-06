package com.example.android.network

import com.google.gson.Gson

class Converter {

    companion object {

        fun <T> parsingJson(json: String, model: Class<T>): T {
            val gson = Gson()

            return gson.fromJson(json, model)
        }

        fun getImageUrl(image: String): String {

            val list = listOf(APIClient.IMAGE_URL, image)
            return list.filter { it.isNotEmpty() }.joinToString("")
        }
    }
}