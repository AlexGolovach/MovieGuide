package com.example.android.network.httprequest

import android.os.AsyncTask
import com.example.android.network.NetworkCallback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL

class HttpRequest private constructor(){

    companion object {

        @Volatile
        private var instance: HttpRequest? = null

        fun getInstance(): HttpRequest?{
            if (instance == null){
                synchronized(HttpRequest::class.java){
                    if (instance == null){
                        instance =
                            HttpRequest()
                    }
                }
            }

            return instance
        }
    }

    fun load(url: String, callback: NetworkCallback<String>) {
        if (url != "") {
            LoaderTask(callback).execute(url)
        } else {
            //TODO don't use NullPointer for own exceptions
            callback.onError(NullPointerException("Url must not be null"))
        }
    }

    private class LoaderTask(var callback: NetworkCallback<String>) :
        AsyncTask<String, Void, String>() {

        lateinit var url: String

        override fun doInBackground(vararg urls: String): String? {
            url = urls[0]

            val url = URL(url)

            val client = OkHttpClient()
            val request = Request.Builder()
                .get()
                .url(url)
                .build()

            return client.newCall(request).execute().body().string()
        }

        override fun onPostExecute(string: String) {
            callback.onSuccess(string)
        }
    }
}