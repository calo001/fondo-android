package com.github.calo001.fondo.network

import com.github.calo001.fondo.config.Constants
import com.github.calo001.fondo.model.DownloadLinkResponse
import com.github.calo001.fondo.model.Photo
import com.github.calo001.fondo.model.Result
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url


interface UnsplashApiService {
    @GET("photos")
    fun todayPhotos(@Query("page") page: Int,
                    @Query("per_page") perPage: Int,
                    @Query("order_by") orderBy: String): Observable<List<Photo>>

    @GET("search")
    fun searchPhotos(@Query("query") query: String,
                     @Query("page") page: Int,
                     @Query("per_page") perPage: Int): Observable<Result>

    @GET("download")
    fun getLinkLocation(@Query("id") id: String): Observable<DownloadLinkResponse>

    @GET
    @Streaming
    fun downloadImage(@Url url: String): Observable<ResponseBody>

    companion object {
        fun create(): UnsplashApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.API_UNSPLASH)
                .build()

            return retrofit.create(UnsplashApiService::class.java)
        }
    }
}