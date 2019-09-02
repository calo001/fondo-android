package com.github.calo001.fondo.ui.detail

import com.github.calo001.fondo.network.ApiError

class PhotoDetailPresenterImpl(val view: PhotoDetailViewContract):
    PhotoDetailPresenterContract {
    val interactor: PhotoDetailInteractorContract =
        PhotoDetailInteractorImpl(this)

    override fun getDownloadLink(id: String) {
        interactor.getDownloadLink(id)
    }

    override fun onDownloadLinkSuccess(url: String) {
        view.onSuccess(url)
    }

    override fun onError(error: ApiError) {
        view.onError(error)
    }
}
