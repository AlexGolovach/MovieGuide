package com.example.android.movie.ui.main.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.movie.R
import com.example.android.movie.ui.base.BaseFragment
import com.example.android.movie.ui.utils.Constants.VIDEO_ID
import kotlinx.android.synthetic.main.fragment_player.*

class PlayerFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_player, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkInternetConnection()

        btnTryAgain.setOnClickListener {
            checkInternetConnection()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun checkInternetConnection() {
        if (isOnline()) {
            constraintLayout.visibility = View.GONE
            webView.visibility = View.VISIBLE

            val videoId = arguments?.getString(VIDEO_ID)

            val video =
                "<iframe width=\"100%\" height=\"100%\" src=\"$videoId\" frameborder=\"0\" allowfullscreen></iframe>"

            webView.apply {
                loadData(video, "text/html", "utf-8")
                settings.javaScriptEnabled = true
            }
        } else {
            constraintLayout.visibility = View.VISIBLE
            webView.visibility = View.GONE
        }
    }
}