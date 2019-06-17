package com.example.android.movie.ui.base

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

abstract class BaseActivity : AppCompatActivity() {

    fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val netInfo = cm!!.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    fun checkConnection() {
        if (isOnline()) {
            Toast.makeText(this, "You are connected to Internet", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(
                this,
                "You are not connected to Internet",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}