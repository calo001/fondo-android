package com.github.calo001.fondo.util

import android.util.Log
import com.github.calo001.fondo.repository.UnsplashRepository
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FondoDownloadManager(private val listener: DownloadListener) {

    private var mProgress: Int = 0

    fun downloadImage(url: String?, fileName: String?, externalDirectory: String) {
        if (url != null && fileName != null) {
            download(url, fileName, externalDirectory)
        }
    }

    private fun download(url: String, fileName: String, externalDirectory: String) {
        UnsplashRepository.downloadImage(url)
            .subscribe(object: Observer<ResponseBody> {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe!")
                    //d.dispose()
                }

                override fun onNext(response: ResponseBody) {
                    try {
                        saveImage(response, externalDirectory, fileName)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, e.localizedMessage)
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                }

            })
    }

    @Throws(IOException::class)
    private fun saveImage(body: ResponseBody, externalDirectory: String, fileName: String) {
        var count: Int
        val data = ByteArray(1024 * 4)
        val fileSize = body.contentLength()
        val inputStream = BufferedInputStream(body.byteStream(), 1024 * 8)
        val outputFile = File(externalDirectory, fileName)
        val outputStream = FileOutputStream(outputFile)
        var total: Long = 0

        count = inputStream.read(data)
        while (count != -1) {
            total += count.toLong()
            mProgress = ((total * 100).toDouble() / fileSize.toDouble()).toInt()
            listener.onProgressChange(mProgress)
            outputStream.write(data, 0, count)

            count = inputStream.read(data)
        }

        outputStream.flush()
        outputStream.close()
        inputStream.close()
        listener.onDownloadComplete(outputFile)
    }

    companion object {
        const val TAG = "FondoDownloadManager"
    }

    interface DownloadListener {
        fun onProgressChange(progress: Int)
        fun onDownloadComplete(outputFile: File)
    }
}