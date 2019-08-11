package io.codyffly.fondo.service

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.ThumbnailUtils
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.core.app.NotificationCompat
import io.codyffly.fondo.R
import io.codyffly.fondo.repository.UnsplashRepository
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class BackgroundNotificationService: IntentService(DOWNLOAD_SERVICE) {

    private lateinit var mNotificationBuilder: NotificationCompat.Builder
    private lateinit var mNotificationManager: NotificationManager
    private lateinit var mUrl: String

    private var mTimePreviousProgress: Long = Calendar.getInstance().timeInMillis
    private var mProgress: Int = 0
    private var mPhotoFileName: String = "default.jpg"

    override fun onHandleIntent(intent: Intent?) {
        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        getURLfromExtras(intent)
        setupNotificationChannel()
        setupNotificationBuilder()
        setupIntentData(intent)

        downloadImage()
    }

    private fun getURLfromExtras(intent: Intent?) {
        intent?.let {
            mUrl = it.getStringExtra(URL_EXTRA)
        }
    }

    private fun setupNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                ID_DOWNLOAD_NOTIFICATION,
                NAME_DOWNLOAD_NOTIFICATION,
                NotificationManager.IMPORTANCE_DEFAULT)

            with(notificationChannel) {
                setSound(null, null)
                enableLights(false)
                lightColor = Color.GREEN
                enableVibration(false)

                mNotificationManager.createNotificationChannel(this)
            }
        }
    }

    private fun setupNotificationBuilder(){
        mNotificationBuilder = NotificationCompat.Builder(this, ID_DOWNLOAD_NOTIFICATION)
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .setContentTitle(resources.getString(R.string.download))
            .setContentText(resources.getString(R.string.downloadingWallpaper))
            .setDefaults(0)
            .setProgress(0, 0, true)

        mNotificationManager.notify(ID_NOTIFICATION, mNotificationBuilder.build())
    }

    private fun setupIntentData(intent: Intent?) {
        intent?.let {
            mPhotoFileName = "${it.getStringExtra(ID_PHOTO_EXTRA)}.jpg"
        }
    }

    private fun downloadImage() {
        UnsplashRepository.downloadImage(mUrl)
            .subscribe(object: Observer<ResponseBody> {
                override fun onSubscribe(d: Disposable) {
                    Toast.makeText(this@BackgroundNotificationService, "Complete!", Toast.LENGTH_SHORT).show()
                }

                override fun onNext(response: ResponseBody) {
                    try {
                        saveImage(response)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_SHORT).show()
                }

                override fun onComplete() {
                    Toast.makeText(this@BackgroundNotificationService, "Complete!", Toast.LENGTH_SHORT).show()
                }

            })
    }

    @Throws(IOException::class)
    private fun saveImage(body: ResponseBody) {
        var count: Int
        val data = ByteArray(1024 * 4)
        val fileSize = body.contentLength()
        val inputStream = BufferedInputStream(body.byteStream(), 1024 * 8)
        val outputFile = File(getExternalDir(), mPhotoFileName)
        val outputStream = FileOutputStream(outputFile)
        var total: Long = 0

        count = inputStream.read(data)
        while (count != -1) {
            total += count.toLong()
            mProgress = ((total * 100).toDouble() / fileSize.toDouble()).toInt()

            updateNotification(mProgress)
            outputStream.write(data, 0, count)

            count = inputStream.read(data)
        }

        onDownloadComplete(outputFile)
        outputStream.flush()
        outputStream.close()
        inputStream.close()
    }

    private fun updateNotification(progress: Int){
        val timeNow = Calendar.getInstance().timeInMillis

        if(timeNow - mTimePreviousProgress > 100) {
            mNotificationBuilder.setProgress(100, progress, false)
            mNotificationBuilder.setContentText("${resources.getString(R.string.downloaded)}: $progress")

            mNotificationManager.notify(0, mNotificationBuilder.build())
            mTimePreviousProgress = timeNow
        }
    }

    private fun setupWallpaper(file: File) {
        Toast.makeText(this, "DOwnload Complete!", Toast.LENGTH_LONG).show()
    }

    private fun onDownloadComplete(file: File) {
        val thumbnailUtil = ThumbnailUtils.extractThumbnail(
            BitmapFactory.decodeFile(file.absolutePath), 300, 300
        )
        mNotificationManager.cancel(ID_NOTIFICATION)
        with(mNotificationBuilder) {
            setProgress(0, 0, false)
            setContentText(resources.getString(R.string.wallpaperDownloadComplete))
            setSmallIcon(android.R.drawable.stat_sys_download_done)
            setAutoCancel(true)
            setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(thumbnailUtil)
                .bigLargeIcon(null))
        }
        mNotificationManager.notify(ID_NOTIFICATION, mNotificationBuilder.build())

        setupWallpaper(file)
    }

    private fun getExternalDir(): String {
        val state = Environment.getExternalStorageState()
        return if (Environment.MEDIA_MOUNTED == state) {
            getExternalFilesDir(null)!!.absolutePath
        } else {
            filesDir.absolutePath
        }
    }

    companion object {
        const val DOWNLOAD_SERVICE = "download_service"
        const val ID_DOWNLOAD_NOTIFICATION = "id_notification"
        const val NAME_DOWNLOAD_NOTIFICATION = "name_download_notification"
        const val ID_NOTIFICATION = 0
        const val ID_PHOTO_EXTRA = "photo_id"
        const val DEFAULT_NAME = "default.jpg"
        const val URL_EXTRA = "url"
    }

}