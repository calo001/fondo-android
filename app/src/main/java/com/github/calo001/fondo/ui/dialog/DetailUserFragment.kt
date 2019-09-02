package com.github.calo001.fondo.ui.dialog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.calo001.fondo.R
import com.github.calo001.fondo.model.Photo
import kotlinx.android.synthetic.main.dialog_bottom_sheet.*

class DetailUserFragment(private val photo: Photo) : RoundedBottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(view)
            .load(photo.user.profile_image.medium)
            .apply(RequestOptions.circleCropTransform())
            .into(userPicture)

        txtUsername.text = photo.user.username
        txtUserBio.text = photo.user.bio?.trim()
        txtLocation.text = photo.user.location
        txtImgDescription.text = photo.description?.trim()
        txtImgCreatedAt.text = photo.created_at
        txtImgWidth.text = photo.width.toString()
        txtImgHeight.text = photo.height.toString()
        txtImgColor.text = photo.color

        with(txtLocation) {
            if (text.isNullOrEmpty()) visibility = View.GONE
        }

        with(txtUserBio) {
            if (text.isNullOrEmpty()) visibility = View.GONE
        }

        with(txtImgDescription) {
            if (text.isNullOrEmpty()) visibility = View.GONE
        }

        btnVisitProfile.setOnClickListener {
            val url = "https://unsplash.com/@${photo.user.username}?utm_source=Fondo&utm_medium=referral"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}