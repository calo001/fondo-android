package io.codyffly.fondo.repository

import io.codyffly.fondo.config.Constants
import io.codyffly.fondo.model.DownloadLinkResponse
import io.codyffly.fondo.model.Photo
import io.codyffly.fondo.model.Result
import io.codyffly.fondo.network.UnsplashApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

object UnsplashRepository {
    private val client by lazy { UnsplashApiService.create() }

    fun getDailyPhotos(page: Int, perPage: Int = 12, orderBy: String = "latest"): Observable<List<Photo>> {
        return client.dailyPhotos(Constants.API_KEY_UNSPLASH, page, perPage, orderBy)
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