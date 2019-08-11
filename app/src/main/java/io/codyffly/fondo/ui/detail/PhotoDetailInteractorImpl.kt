package io.codyffly.fondo.ui.detail

import android.annotation.SuppressLint
import io.codyffly.fondo.repository.UnsplashRepository

class PhotoDetailInteractorImpl(private val presenter: PhotoDetailPresenterContract): PhotoDetailInteractorContract {

    @SuppressLint("CheckResult")
    override fun getDownloadLink(id: String) {
        UnsplashRepository.getDownloadLinkLocation(id)
            .subscribe({ response ->
                presenter.onSuccess(response.url)
            }, { error ->
                presenter.onError(error.localizedMessage)
            })
    }

}