package com.example.android.movie.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.example.android.movie.ui.main.HomeActivity
import com.example.android.movie.ui.register.RegisterActivity
import com.example.android.movie.ui.utils.AccountOperation

class SplashScreen : AppCompatActivity() {

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handler = Handler()
        handler.postDelayed({

            if (AccountOperation.isAccountSaved()) {
                startActivity(Intent(this, HomeActivity::class.java))

                finish()
            } else {
                startActivity(Intent(this, RegisterActivity::class.java))

                finish()
            }

        }, SPLASH_DISPLAY_LENGTH)
    }

    override fun onDestroy() {
        super.onDestroy()

        handler.removeCallbacksAndMessages(null)
    }

    companion object {
        private const val SPLASH_DISPLAY_LENGTH = 1000L
    }
}