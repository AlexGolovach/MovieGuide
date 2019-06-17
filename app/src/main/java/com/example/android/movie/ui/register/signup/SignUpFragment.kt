package com.example.android.movie.ui.register.signup

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android.database.model.User
import com.example.android.movie.R
import com.example.android.movie.mvp.signup.ISignUpPresenter
import com.example.android.movie.mvp.signup.ISignUpView
import com.example.android.movie.ui.base.BaseFragment
import com.example.android.movie.ui.main.HomeActivity
import com.example.android.database.IdGenerator
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : BaseFragment(), ISignUpView {

    private lateinit var signUpPresenter: ISignUpPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_sign_up, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpPresenter = SignUpPresenter(this)

        initToolbar()

        btnSignUp.setOnClickListener {
            createUser()
        }
    }

    private fun createUser() {
        val username = editUsername.text.toString()
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        val user = User(
            IdGenerator.generateId(),
            username,
            email,
            password
        )

        signUpPresenter.createUser(user, saveAccCheckBox.isChecked)
    }

    private fun initToolbar() {
        toolbar.apply {
            title = getString(R.string.app_name)
            activity?.let { ContextCompat.getColor(it, R.color.white) }
                ?.let { setTitleTextColor(it) }
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                onBack()
            }
        }
    }

    override fun createUserSuccess() {
        startActivity(Intent(activity, HomeActivity::class.java))

        activity?.finish()
    }

    override fun createUserError(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        signUpPresenter.onDestroy()

        super.onDestroy()
    }
}