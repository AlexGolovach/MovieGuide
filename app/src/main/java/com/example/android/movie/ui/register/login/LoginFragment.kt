package com.example.android.movie.ui.register.login

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android.movie.R
import com.example.android.movie.mvp.login.ILoginPresenter
import com.example.android.movie.mvp.login.ILoginView
import com.example.android.movie.ui.main.HomeActivity
import com.example.android.movie.ui.register.signup.SignUpFragment
import com.example.android.movie.ui.utils.TextWatcherAdapter
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment(), ILoginView {

    private lateinit var loginPresenter: ILoginPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        initToolbar(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginPresenter = LoginPresenter(this)

        initListeners()
    }

    private fun initListeners() {
        //TODO rename fields
        editEmail.addTextChangedListener(object : TextWatcherAdapter() {
            override fun afterTextChanged(p0: Editable?) {

                editPassword.addTextChangedListener(object : TextWatcherAdapter() {
                    override fun afterTextChanged(p0: Editable?) {
                        updateButtonState()
                    }
                })
            }
        })

        signUp.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.addToBackStack(SignUpFragment::class.java.name)
                ?.replace(
                    R.id.container,
                    SignUpFragment(),
                    SignUpFragment::class.java.name
                )?.commit()
        }

        btnLogin.setOnClickListener {
            loginPresenter.findUser(editEmail.text.toString(), editPassword.text.toString(), saveAccCheckBox.isChecked)
        }
    }

    override fun findUserSuccess(success: String) {
        Toast.makeText(activity, success, Toast.LENGTH_SHORT).show()

        startActivity(Intent(activity, HomeActivity::class.java))
        activity?.finish()
    }

    override fun findUserError(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
    }

    private fun updateButtonState() {
        btnLogin.isEnabled = true
    }

    private fun initToolbar(view: View) {
        view.toolbar.apply {
            title = getString(R.string.app_name)
        }
    }

    override fun onDestroy() {
        loginPresenter.onDestroy()

        super.onDestroy()
    }
}
