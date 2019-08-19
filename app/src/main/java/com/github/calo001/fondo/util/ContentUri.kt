package com.github.calo001.fondo.util

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore

fun getContentUri(context: Context, path: String): Uri? {
    val cursor = context.contentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        arrayOf(MediaStore.Images.Media._ID),
        MediaStore.Images.Media.DATA + "=? ",
        arrayOf(path), null
    )

    return if(cursor != null && cursor.moveToFirst()) {
        val id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
        Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toString())
    } else if (path.isNotEmpty()) {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.DATA, path)
        context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    } else {
        null
    }
}