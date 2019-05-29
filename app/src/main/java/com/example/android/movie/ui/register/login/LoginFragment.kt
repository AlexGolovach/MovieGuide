package com.example.android.movie.ui.register.login

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.database.model.User
import com.example.android.movie.R
import com.example.android.movie.mvp.login.ILoginPresenter
import com.example.android.movie.mvp.login.ILoginView
import com.example.android.movie.ui.main.HomeActivity
import com.example.android.movie.ui.register.signup.SignUpFragment
import com.example.android.movie.ui.utils.dialogprogress.DialogProgress
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment(), ILoginView {

    private lateinit var loginPresenter: ILoginPresenter
    private val dialogProgress = DialogProgress()

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

    private fun initToolbar(view: View) {
        view.toolbar.apply {
            title = getString(R.string.app_name)
        }
    }

    private fun initListeners() {
        sign_up.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.addToBackStack(SignUpFragment::class.java.name)
                ?.replace(
                    R.id.container,
                    SignUpFragment(),
                    SignUpFragment::class.java.name
                )?.commit()
        }

        btn_login.setOnClickListener {
            dialogProgress.show(fragmentManager,"dialog_progress")
            loginPresenter.findUser(edit_email.text.toString(), edit_password.text.toString())
        }
    }

    override fun entrySuccess(user: User) {
        dialogProgress.dismiss()

        startActivity(Intent(activity, HomeActivity::class.java))
        activity?.finish()
    }

    override fun entryError(error: Throwable) {
        if (error is NullPointerException) {

            val builder = AlertDialog.Builder(activity)
            builder.setTitle(getString(R.string.error))
                .setMessage(getString(R.string.problem_with_entry))
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                    dialogProgress.dismiss()
                    dialog.cancel()
                }
                .create()
                .show()
        }
    }

    override fun onDestroy() {
        loginPresenter.onDestroy()

        super.onDestroy()
    }
}
