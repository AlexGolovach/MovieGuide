package com.example.android.movie.register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.android.movie.R

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        showFragment()
    }

    private fun showFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.container, LoginFragment()).commit()
    }
}