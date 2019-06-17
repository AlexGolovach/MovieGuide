package com.example.android.movie.ui.utils

import ImageLoader
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.movie.R
import kotlinx.android.synthetic.main.dialog_image.*
import java.lang.ref.WeakReference

class DialogImageFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.dialog_image, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        getImage()
    }

    private fun getImage() {
        val imageUrl = arguments?.getString("IMAGE_URL")

        if (imageUrl != null) {
            ImageLoader.getInstance()?.load(imageUrl, WeakReference(actorImage))
        }
    }
}