package com.github.calo001.fondo.manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.github.calo001.fondo.R
import java.util.*

class FondoNotificationManager(val context: Context) {
    private var mNotificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private lateinit var mNotificationBuilder: NotificationCompat.Builder
    private var mTimePreviousProgress: Long = Calendar.getInstance().timeInMillis
    private val mNotificationID = FondoSharePreferences.getNextNotificationCount()

    init {
        setupNotificationChannel()
        setupNotificationBuilder()
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
        mNotificationBuilder = NotificationCompat.Builder(context,
            ID_DOWNLOAD_NOTIFICATION
        )
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .setContentTitle(context.resources.getString(R.string.download))
            .setContentText(context.resources.getString(R.string.downloadingWallpaper))
            .setDefaults(0)
            .setProgress(0, 0, true)

        mNotificationManager.notify(mNotificationID, mNotificationBuilder.build())
    }

    fun updateNotification(progress: Int){
        val timeNow = Calendar.getInstance().timeInMillis

        if(timeNow - mTimePreviousProgress > 100) {
            mNotificationBuilder.setProgress(100, progress, false)
            mNotificationBuilder.setContentText("${context.resources.getString(R.string.downloaded)}: $progress%")
            mNotificationManager.notify(mNotificationID, mNotificationBuilder.build())
            mTimePreviousProgress = timeNow
        }
    }

    fun updateNotificationForTerminate(thumbnailUtil: Bitmap){
        mNotificationManager.cancel(mNotificationID)
        with(mNotificationBuilder) {
            setProgress(0, 0, false)
            setContentText(context.resources.getString(R.string.wallpaperDownloadComplete))
            setSmallIcon(android.R.drawable.stat_sys_download_done)
            setAutoCancel(true)
            setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(thumbnailUtil)
                .bigLargeIcon(null))
        }
        mNotificationManager.notify(mNotificationID, mNotificationBuilder.build())
    }

    companion object {
        const val ID_DOWNLOAD_NOTIFICATION = "id_notification"
        const val NAME_DOWNLOAD_NOTIFICATION = "name_download_notification"
    }
}