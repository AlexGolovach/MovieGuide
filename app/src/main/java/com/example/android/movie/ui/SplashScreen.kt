package com.example.android.movie.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.example.android.database.Callback
import com.example.android.database.Injector
import com.example.android.database.model.User
import com.example.android.movie.ui.main.HomeActivity
import com.example.android.movie.ui.register.RegisterActivity

class SplashScreen : AppCompatActivity(), Callback<User> {

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handler = Handler()
        handler.postDelayed({

            Injector.getUserRepositoryImpl().firstEntry(this)

        }, SPLASH_DISPLAY_LENGTH.toLong())
    }

    override fun onSuccess(result: User) {
        startActivity(Intent(this, HomeActivity::class.java))

        finish()
    }

    override fun onError(throwable: Throwable) {
        if (throwable is NullPointerException) {
            startActivity(Intent(this, RegisterActivity::class.java))

            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        handler.removeCallbacksAndMessages(null)
    }

    companion object {
        private const val SPLASH_DISPLAY_LENGTH = 1000
    }
}