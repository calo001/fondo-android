package io.codyffly.fondo.service

import android.app.IntentService
import android.app.WallpaperManager
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import io.codyffly.fondo.util.*
import java.io.File
import java.io.IOException

class NotificationService: IntentService(DOWNLOAD_SERVICE), FondoDownloadManager.DownloadListener {
    private lateinit var mNotificationManager: FondoNotificationManager
    private lateinit var mDownloadManager: FondoDownloadManager
    private var mUrl: String? = null
    private var mPhotoFileName: String? = null

    override fun onHandleIntent(intent: Intent?) {
        getURLfromExtras(intent)
        setupIntentData(intent)
        mNotificationManager = FondoNotificationManager(this)
        mDownloadManager = FondoDownloadManager(this)
        mDownloadManager.downloadImage(mUrl, mPhotoFileName, getExternalDir(this))
    }

    private fun getURLfromExtras(intent: Intent?) {
        intent?.let {
            mUrl = it.getStringExtra(URL_EXTRA)
        }
    }

    private fun setupIntentData(intent: Intent?) {
        intent?.let {
            mPhotoFileName = "${it.getStringExtra(ID_PHOTO_EXTRA)}.jpg"
        }
    }

    override fun onProgressChange(progress: Int) {
        mNotificationManager.updateNotification(progress)
    }

    override fun onDownloadComplete(outputFile: File) {
        val thumbnailImage = ThumbnailUtils.extractThumbnail(
            BitmapFactory.decodeFile(outputFile.absolutePath), 300, 300
        )

        mNotificationManager.updateNotificationForTerminate(thumbnailImage)
        setupWallpaper(outputFile)
    }

    private fun setupWallpaper(file: File) {
        val wallpaperManager = FondoWallpaperManager(this, file.absolutePath)
        wallpaperManager.setWallpaper()
    }

    companion object {
        const val TAG = "NotificationService"
        const val DOWNLOAD_SERVICE = "download_service"
        const val ID_PHOTO_EXTRA = "photo_id"
        const val URL_EXTRA = "url"
    }

}