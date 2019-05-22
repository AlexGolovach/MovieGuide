package com.example.android.movie.ui.register.signup

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android.database.model.User
import com.example.android.movie.R
import com.example.android.movie.mvp.signup.ISignUpPresenter
import com.example.android.movie.mvp.signup.ISignUpView
import com.example.android.movie.ui.main.HomeActivity
import com.example.android.movie.ui.utils.IdGenerator
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*

class SignUpFragment : Fragment(), ISignUpView {

    private lateinit var signUpPresenter: ISignUpPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        initToolbar(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpPresenter = SignUpPresenter(this)

        btn_sign_up.setOnClickListener {
            createUser()
        }
    }

    private fun createUser() {
        val username = edit_username.text.toString()
        val email = edit_email.text.toString()
        val password = edit_password.text.toString()

        val user = User(
            IdGenerator.generateId(),
            username,
            email,
            password
        )

        signUpPresenter.createUser(user, save_acc_check_box.isChecked)
    }

    private fun initToolbar(view: View) {
        view.toolbar.apply {
            title = getString(R.string.app_name)
            setTitleTextColor(resources.getColor(R.color.white))
        }
    }

    override fun createUserSuccess(success: String) {
        Toast.makeText(activity, success, Toast.LENGTH_SHORT).show()

        startActivity()
    }

    override fun createUserError(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
    }

    private fun startActivity() {
        startActivity(Intent(activity, HomeActivity::class.java))

        activity?.finish()
    }

    override fun onDestroy() {
        signUpPresenter.onDestroy()

        super.onDestroy()
    }
}