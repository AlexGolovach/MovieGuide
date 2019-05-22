package com.example.android.movie.ui.profile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.android.movie.R
import com.example.android.movie.ui.utils.AccountOperation
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)

        initToolbar()
        getAccountInformation()
    }

    private fun initToolbar() {
        toolbar.title = resources.getString(R.string.profile)
    }

    private fun getAccountInformation() {
        username.text = AccountOperation.getAccount().login
        email.text = AccountOperation.getAccount().email
        password.text = AccountOperation.getAccount().password
    }
}