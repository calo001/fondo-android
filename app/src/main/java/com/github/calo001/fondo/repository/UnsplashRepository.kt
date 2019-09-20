package com.github.calo001.fondo.repository

import com.github.calo001.fondo.model.DownloadLinkResponse
import com.github.calo001.fondo.model.Photo
import com.github.calo001.fondo.model.Result
import com.github.calo001.fondo.network.UnsplashApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

object UnsplashRepository {
    private val client by lazy { UnsplashApiService.create() }

    fun getTodayPhotos(page: Int, perPage: Int = 30, orderBy: String = "latest"): Observable<List<Photo>> {
        return client.todayPhotos(page, perPage, orderBy)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getQueryPhotos(query: String, page: Int, perPage: Int = 30): Observable<Result> {
        val cleanQuery = query.replace(" ","-")
        return client.searchPhotos(cleanQuery, page, perPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getDownloadLinkLocation(id: String): Observable<DownloadLinkResponse> {
        return client.getLinkLocation(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun downloadImage(url: String): Observable<ResponseBody> {
        return client.downloadImage(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}