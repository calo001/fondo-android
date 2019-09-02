package com.github.calo001.fondo.ui.detail

import android.annotation.SuppressLint
import com.github.calo001.fondo.network.ApiError
import com.github.calo001.fondo.repository.UnsplashRepository

class PhotoDetailInteractorImpl(private val presenter: PhotoDetailPresenterContract):
    PhotoDetailInteractorContract {

    @SuppressLint("CheckResult")
    override fun getDownloadLink(id: String) {
        UnsplashRepository.getDownloadLinkLocation(id)
            .subscribe({ response ->
                presenter.onDownloadLinkSuccess(response.url)
            }, { error ->
                val errorApi = ApiError(error)
                presenter.onError(errorApi)
            })
    }
}