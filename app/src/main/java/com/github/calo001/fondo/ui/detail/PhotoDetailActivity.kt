package com.github.calo001.fondo.ui.detail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.github.calo001.fondo.R
import com.github.calo001.fondo.config.Constants.PERMISSION_REQUEST_CODE
import com.github.calo001.fondo.manager.history.HistoryManager
import com.github.calo001.fondo.model.Photo
import com.github.calo001.fondo.network.ApiError
import com.github.calo001.fondo.service.NotificationService
import com.github.calo001.fondo.ui.dialog.DetailUserFragment
import com.github.calo001.fondo.util.makeStatusBarTransparent
import com.github.calo001.fondo.util.setMarginTop
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_photo_detail.*
import kotlinx.android.synthetic.main.progress_layout.*

class PhotoDetailActivity : AppCompatActivity(), OnSetAsWallpaperListener,
    PhotoDetailViewContract {
    private lateinit var detailFragment: DetailUserFragment
    private lateinit var mCurrentPhoto: Photo
    private lateinit var mDownloadLink: String
    private val presenter = PhotoDetailPresenterImpl(this)
    private val historyManager = HistoryManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)

        setSupportActionBar(toolbarDetail)
        setupStatusbar()
        getExtraInfo()
        setupFragment()
        setupEvents()
        showImage()
    }

    private fun setupStatusbar() {
        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val color = ContextCompat.getColor(this, android.R.color.transparent)
        makeStatusBarTransparent(color)
        ViewCompat.setOnApplyWindowInsetsListener(detailContainer) { _, insets ->
            toolbarDetail.setMarginTop(insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }
    }

    private fun setupEvents() {
        fabWallpaper.setOnClickListener {
            presenter.getDownloadLink(mCurrentPhoto.id)
            historyManager.addToHistory(mCurrentPhoto)
            (it as FloatingActionButton).hide()
        }
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
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    finish()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progress.visibility = View.GONE
                    return false
                }
            })
            .thumbnail(0.01f)
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(bigImageView)
    }

    private fun getExtraInfo() {
        mCurrentPhoto = intent?.extras?.getSerializable(EXTRA_OBJECT) as Photo
    }

    private fun setupFragment() {
        detailFragment = DetailUserFragment( mCurrentPhoto)
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

    override fun onError(error: ApiError) {
        Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.showMore -> {
                detailFragment.show(supportFragmentManager, detailFragment.tag)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_OBJECT = "PhotoObject"
    }
}

interface OnSetAsWallpaperListener {
    fun onSetWallpaper()
}