package com.github.calo001.fondo.network

import com.github.calo001.fondo.config.Constants
import com.github.calo001.fondo.model.DownloadLinkResponse
import com.github.calo001.fondo.model.Photo
import com.github.calo001.fondo.model.Result
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import java.util.*


interface UnsplashApiService {
    @GET("photos/")
    fun todayPhotos(@Query("client_id") clientID: String,
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
            val cipherSuites = ArrayList<CipherSuite>()
            cipherSuites.addAll(ConnectionSpec.MODERN_TLS.cipherSuites()!!)
            cipherSuites.add(CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA)
            cipherSuites.add(CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA)

            val legacyTls = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .cipherSuites(*cipherSuites.toTypedArray())
                .build()

            val client = OkHttpClient.Builder()
                .connectionSpecs(listOf(legacyTls, ConnectionSpec.CLEARTEXT))
                .build()

            val retrofit = Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.API_UNSPLASH)
                .build()

            return retrofit.create(UnsplashApiService::class.java)
        }
    }
}