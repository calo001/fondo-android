package com.github.calo001.fondo.base

import android.annotation.SuppressLint
import com.github.calo001.fondo.model.Photo
import com.github.calo001.fondo.network.ApiError
import com.github.calo001.fondo.repository.HistoryRepository
import com.github.calo001.fondo.repository.UnsplashRepository

abstract class BasePhotoInteractorImpl<P : BasePhotoPresenterContract>(open val presenter: P) :
    BasePhotoInteractorContract {
    @SuppressLint("CheckResult")
    override fun getDownloadLink(id: String) {
        UnsplashRepository.getDownloadLinkLocation(id)
            .subscribe({ response ->
                presenter.onDownloadLinkSuccess(response.url)
            }, { error ->
                val apiError = ApiError(error)
                presenter.onError(apiError)
            })
    }
}