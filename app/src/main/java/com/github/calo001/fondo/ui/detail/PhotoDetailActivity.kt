package com.github.calo001.fondo.ui.detail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.calo001.fondo.R
import com.github.calo001.fondo.dialog.DetailUserFragment
import com.github.calo001.fondo.model.Photo
import com.github.calo001.fondo.service.NotificationService
import com.github.calo001.fondo.util.makeStatusBarTransparent
import kotlinx.android.synthetic.main.activity_photo_detail.*
import kotlin.math.max
import kotlin.math.min

class PhotoDetailActivity : AppCompatActivity(), OnSetAsWallpaperListener,
    PhotoDetailViewContract {
    private lateinit var detailFragment: DetailUserFragment
    private lateinit var mCurrentPhoto: Photo
    private lateinit var mScaleGestureDetector: ScaleGestureDetector
    private lateinit var mDownloadLink: String
    private val presenter = PhotoDetailPresenterImpl(this)
    private var mScaleFactor = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)

        setupStatusbar()
        getExtraInfo()
        setupFragment()
        setupEvents()
        showImage()
    }

    private fun setupStatusbar() {
        makeStatusBarTransparent("#00000000")
    }

    private fun setupEvents() {
        fabWallpaper.setOnClickListener {
            presenter.getDownloadLink(mCurrentPhoto.id)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mScaleGestureDetector.onTouchEvent(event)
        return true
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun startImageDownload() {
        val intent = Intent(this, NotificationService::class.java)
        intent.putExtra(NotificationService.URL_EXTRA, mDownloadLink)
        intent.putExtra(NotificationService.ID_PHOTO_EXTRA, mCurrentPhoto.id)
        startService(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startImageDownload()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showImage() {
        Glide.with(this)
            .load(mCurrentPhoto.urls.regular)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(bigImageView)

        mScaleGestureDetector = ScaleGestureDetector(this, object: ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector?): Boolean {
                mScaleFactor *= detector?.scaleFactor!!
                mScaleFactor = max(0.9f, min(mScaleFactor, 10.0f))
                bigImageView.scaleX = mScaleFactor
                bigImageView.scaleY = mScaleFactor

                return true
            }
        })

    }

    private fun getExtraInfo() {
        mCurrentPhoto = intent?.extras?.getSerializable(EXTRA_OBJECT) as Photo
    }

    private fun setupFragment() {
        detailFragment = DetailUserFragment(this, mCurrentPhoto)
    }

    fun showDetails(view: View) {
        detailFragment.show(supportFragmentManager, detailFragment.tag)
    }

    override fun onSetWallpaper() {
        presenter.getDownloadLink(mCurrentPhoto.id)
    }

    override fun onSuccess(image: String) {
        mDownloadLink = image
        if (checkPermission()) {
            startImageDownload()
        } else {
            requestPermission()
        }
    }

    override fun onError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 1
        const val EXTRA_OBJECT = "PhotoObject"
    }
}

interface OnSetAsWallpaperListener {
    fun onSetWallpaper()
}