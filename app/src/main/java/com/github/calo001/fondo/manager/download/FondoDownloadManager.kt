package com.github.calo001.fondo.manager.download

import com.github.calo001.fondo.repository.UnsplashRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FondoDownloadManager(private val listener: DownloadListener) {

    private var mProgress: Int = 0

    fun downloadImage(url: String?, fileName: String?, externalDirectory: String) {
        if (url != null && fileName != null) {
            val file = File(externalDirectory, fileName)
            if(file.exists()) {
                listener.onDownloadComplete(file)
            } else {
                download(url, fileName, externalDirectory)
            }
        }
    }

    private fun download(url: String, fileName: String, externalDirectory: String) {
        UnsplashRepository.downloadImage(url)
            .flatMap {
                saveImage(it, externalDirectory, fileName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
            .doOnComplete {
                listener.onDownloadComplete(File(externalDirectory, fileName))
            }
            .subscribe()
    }

    @Throws(IOException::class)
    private fun saveImage(body: ResponseBody, externalDirectory: String, fileName: String): Observable<File> {
        return Observable.create {
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

                it.onNext(outputFile)
            }

            outputStream.flush()
            outputStream.close()
            inputStream.close()

            it.onComplete()
        }
    }

    companion object {
        const val TAG = "FondoDownloadManager"
    }

    interface DownloadListener {
        fun onProgressChange(progress: Int)
        fun onDownloadComplete(outputFile: File)
    }
}