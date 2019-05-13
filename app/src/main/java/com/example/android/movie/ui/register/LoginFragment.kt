package com.example.android.movie.ui.register

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.movie.R
import com.example.android.movie.ui.main.HomeActivity
import com.example.android.movie.ui.widget.TextWatcherAdapter
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {

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

        initListeners()
    }

    private fun initListeners() {

        edit_email.addTextChangedListener(object : TextWatcherAdapter() {
            override fun afterTextChanged(p0: Editable?) {

                edit_password.addTextChangedListener(object : TextWatcherAdapter() {
                    override fun afterTextChanged(p0: Editable?) {
                        updateButtonState()
                    }
                })
            }
        })

        sign_up.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.container,
                SignUpFragment()
            )?.commit()
        }

        btn_login.setOnClickListener {
            startActivity(Intent(activity, HomeActivity::class.java))

            activity?.finish()
        }
    }

    private fun updateButtonState() {
        btn_login.isEnabled = true
    }

    private fun initToolbar(view: View) {
        view.toolbar.apply {
            title = getString(R.string.app_name)
        }
    }
}
