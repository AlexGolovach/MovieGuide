package com.example.android.movie.ui.utils.dialogimage

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.movie.R
import com.example.android.movie.mvp.dialogimage.IDialogImagePresenter
import com.example.android.movie.mvp.dialogimage.IDialogImageView
import kotlinx.android.synthetic.main.dialog_image.*

class DialogImageFragment : DialogFragment(),
    IDialogImageView {

    private lateinit var dialogImagePresenter: IDialogImagePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.dialog_image, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogImagePresenter = DialogImagePresenter(this)

        getImage()
    }

    private fun getImage() {
        val imageUrl = arguments?.getString("IMAGE_URL")

        if (imageUrl != null) {
            dialogImagePresenter.onDownloadImage(imageUrl)
        }
    }

    override fun onDownloadActorImage(image: Bitmap) {
        actor_image.setImageBitmap(image)
    }

    override fun onDestroyView() {
        dialogImagePresenter.onDestroy()
        super.onDestroyView()
    }
}