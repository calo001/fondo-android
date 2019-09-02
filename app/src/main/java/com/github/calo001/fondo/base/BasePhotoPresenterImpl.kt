package com.github.calo001.fondo.base

import com.github.calo001.fondo.network.ApiError

abstract class BasePhotoPresenterImpl<V: BasePhotoViewContract, I : BasePhotoInteractorContract>
    (open val view: V): BasePhotoPresenterContract {

    abstract val interactor: I

    override fun onError(error: ApiError) {
        view.onError(error)
    }

    override fun getDownloadLink(id: String) {
        interactor.getDownloadLink(id)
    }

    override fun onDownloadLinkSuccess(url: String) {
        view.onDownloadLinkSuccess(url)
    }
}