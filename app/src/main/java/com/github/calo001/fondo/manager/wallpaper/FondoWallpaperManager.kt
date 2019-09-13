package com.github.calo001.fondo.manager.wallpaper

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.os.Build
import com.github.calo001.fondo.util.getContentUri

class FondoWallpaperManager(val context: Context, val path: String) {
    fun setWallpaper() {
        val uri = getContentUri(context, path)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            try {
                val wallpaperIntent =
                    WallpaperManager.getInstance(context).getCropAndSetWallpaperIntent(uri)
                wallpaperIntent.setDataAndType(uri, "image/*")
                wallpaperIntent.putExtra("mimeType", "image/*")
                context.startActivity(wallpaperIntent)
            } catch (e: Exception) {
                val wallpaperIntent = Intent(Intent.ACTION_ATTACH_DATA)
                wallpaperIntent.setDataAndType(uri, "image/*")
                wallpaperIntent.putExtra("mimeType", "image/*")
                wallpaperIntent.addCategory(Intent.CATEGORY_DEFAULT)
                wallpaperIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                wallpaperIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                wallpaperIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                val chooserIntent = Intent.createChooser(
                    wallpaperIntent,
                    "Set as wallpaper"
                )
                chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(chooserIntent)
            }
        }
    }
}