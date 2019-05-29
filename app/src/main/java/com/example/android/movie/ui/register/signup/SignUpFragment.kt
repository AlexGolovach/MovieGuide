package com.example.android.movie.ui.register.signup

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android.database.model.User
import com.example.android.movie.R
import com.example.android.movie.mvp.signup.ISignUpPresenter
import com.example.android.movie.mvp.signup.ISignUpView
import com.example.android.movie.ui.main.HomeActivity
import com.example.android.movie.ui.utils.dialogprogress.DialogProgress
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*

class SignUpFragment : Fragment(), ISignUpView {

    private lateinit var signUpPresenter: ISignUpPresenter
    private val dialogProgress = DialogProgress()

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
            dialogProgress.show(fragmentManager, "dialog_progress")
            createUser()
        }
    }

    private fun initToolbar(view: View) {
        view.toolbar.apply {
            title = getString(R.string.app_name)
            setTitleTextColor(resources.getColor(R.color.white))
        }
    }

    private fun createUser() {
        val username = edit_username.text.toString()
        val email = edit_email.text.toString()
        val password = edit_password.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            signUpPresenter.createUser(username, email, password)
        } else {
            showDialog(getString(R.string.fields_must_not_be_empty))
        }
    }

    override fun createUserSuccess(user: User) {
        dialogProgress.dismiss()

        startActivity(Intent(activity, HomeActivity::class.java))

        activity?.finish()
    }

    override fun createUserError(error: Throwable) {
        if (error is NullPointerException) {
            showDialog(getString(R.string.problem_with_entry))
        }
    }

    private fun showDialog(message: String) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(getString(R.string.error))
            .setMessage(message)
            .setIcon(R.mipmap.ic_launcher)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialogProgress.dismiss()
                dialog.cancel()
            }
            .create()
            .show()
    }

    override fun onDestroy() {
        signUpPresenter.onDestroy()

        super.onDestroy()
    }
}