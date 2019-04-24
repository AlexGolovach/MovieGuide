package com.example.android.movie

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.example.android.movie.register.RegisterActivity

class SplashScreen : AppCompatActivity() {

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handler = Handler()
        handler.postDelayed({
            startActivity(Intent(this, RegisterActivity::class.java))

            finish()

        }, SPLASH_DISPLAY_LENGTH.toLong())
    }

    override fun onDestroy() {
        super.onDestroy()

        handler.removeCallbacksAndMessages(null)
    }

    companion object {
        private const val SPLASH_DISPLAY_LENGTH = 1000
    }
}