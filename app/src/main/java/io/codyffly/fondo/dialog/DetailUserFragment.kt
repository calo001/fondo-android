package io.codyffly.fondo.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.codyffly.fondo.R
import io.codyffly.fondo.model.Photo
import io.codyffly.fondo.ui.detail.OnSetAsWallpaperListener
import kotlinx.android.synthetic.main.dialog_bottom_sheet.*

class DetailUserFragment(private val listener: OnSetAsWallpaperListener, private val photo: Photo) : RoundedBottomSheetDialogFragment() {
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
        txtUserBio.text = photo.user.bio
        txtImgDescription.text = photo.description
        txtImgCreatedAt.text = photo.created_at
        txtImgWidth.text = photo.width.toString()
        txtImgHeight.text = photo.height.toString()
        txtImgColor.text = photo.color

        with(txtUserBio) {
            if (text.isNullOrEmpty()) visibility = View.GONE
        }

        with(txtImgDescription) {
            if (text.isNullOrEmpty()) visibility = View.GONE
        }
    }
}