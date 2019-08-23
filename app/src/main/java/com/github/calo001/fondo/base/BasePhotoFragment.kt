package com.github.calo001.fondo.base

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.calo001.fondo.model.Photo
import com.github.calo001.fondo.service.NotificationService
import com.github.calo001.fondo.ui.detail.PhotoDetailActivity

abstract class BasePhotoFragment : Fragment() {
    var tmpPhoto: Photo? = null
    var downloadLink: String? = null

    fun setAsWallpaper() {
        if (checkPermission()) {
            startImageDownload()
        } else {
            requestPermission()
        }
    }

    private fun checkPermission(): Boolean {
        context?.let {
            val result = ContextCompat.checkSelfPermission(it, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            return result == PackageManager.PERMISSION_GRANTED
        }
        return false
    }

    private fun requestPermission() {
        activity?.let {
            ActivityCompat.requestPermissions(
                it,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PhotoDetailActivity.PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun startImageDownload() {
        val intent = Intent(activity, NotificationService::class.java)
        intent.putExtra(NotificationService.URL_EXTRA, downloadLink)
        intent.putExtra(NotificationService.ID_PHOTO_EXTRA, tmpPhoto?.id)
        activity?.startService(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PhotoDetailActivity.PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startImageDownload()
                } else {
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}