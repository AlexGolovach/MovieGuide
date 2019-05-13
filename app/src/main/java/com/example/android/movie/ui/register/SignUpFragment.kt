package com.example.android.movie.ui.register

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.movie.R
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*

class SignUpFragment : Fragment() {
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

        btn_sign_up.setOnClickListener {
            startFragment()
        }
    }

    private fun initToolbar(view: View) {
        view.toolbar.apply {
            title = getString(R.string.app_name)
            navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                startFragment()
            }
        }
    }

    private fun startFragment() {
        fragmentManager?.beginTransaction()?.replace(R.id.container,
            LoginFragment()
        )
            ?.commit()
    }
}