package com.github.calo001.fondo.repository

import com.github.calo001.fondo.config.Constants
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

    fun getTodayPhotos(page: Int, perPage: Int = 12, orderBy: String = "latest"): Observable<List<Photo>> {
        return client.todayPhotos(Constants.API_KEY_UNSPLASH, page, perPage, orderBy)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getQueryPhotos(query: String, page: Int, perPage: Int = 12): Observable<Result> {
        return client.searchPhotos(Constants.API_KEY_UNSPLASH, query, page, perPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getDownloadLinkLocation(id: String): Observable<DownloadLinkResponse> {
        return client.getLinkLocation(id, Constants.API_KEY_UNSPLASH)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun downloadImage(url: String): Observable<ResponseBody> {
        return client.downloadImage(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}