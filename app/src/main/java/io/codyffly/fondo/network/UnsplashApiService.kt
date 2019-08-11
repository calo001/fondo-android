package io.codyffly.fondo.network

import io.codyffly.fondo.config.Constants
import io.codyffly.fondo.model.DownloadLinkResponse
import io.codyffly.fondo.model.Photo
import io.codyffly.fondo.model.Result
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface UnsplashApiService {
    @GET("photos/")
    fun dailyPhotos(@Query("client_id") clientID: String,
                    @Query("page") page: Int,
                    @Query("per_page") perPage: Int,
                    @Query("order_by") orderBy: String): Observable<List<Photo>>

    @GET("search/photos")
    fun searchPhotos(@Query("client_id") clientID: String,
                     @Query("query") query: String,
                     @Query("page") page: Int,
                     @Query("per_page") perPage: Int): Observable<Result>

    @GET("photos/{id}/download")
    fun getLinkLocation(@Path("id") id: String,
                        @Query("client_id") clientID: String): Observable<DownloadLinkResponse>

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