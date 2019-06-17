package com.example.android.movie.ui.profile

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import com.example.android.movie.R
import com.example.android.movie.ui.utils.AccountOperation
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)

        initToolbar()
        getAccountInformation()

        showPassword.setOnClickListener {
            password.inputType = InputType.TYPE_CLASS_TEXT
        }
    }

    private fun initToolbar() {
        toolbar.apply {
            title = resources.getString(R.string.profile)
            setTitleTextColor(ContextCompat.getColor(this@ProfileActivity, R.color.white))
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun getAccountInformation() {
        username.text = AccountOperation.getAccount().login
        email.text = AccountOperation.getAccount().email
        password.text = AccountOperation.getAccount().password
    }
}