package com.github.calo001.fondo.base

import com.github.calo001.fondo.model.Photo

abstract class BasePhotoPresenter<V: BasePhotoViewContract, I : BasePhotoInteractorContract>
    (open val view: V): BasePhotoPresenterContract {

    abstract val interactor: I

    override fun onError(message: String) {
        view.onError(message)
    }

    override fun getDownloadLink(id: String) {
        interactor.getDownloadLink(id)
    }

    override fun onDownloadLinkSuccess(url: String) {
        view.onDownloadLinkSuccess(url)
    }

    override fun addToHistory(photo: Photo) {
        interactor.addToHistory(photo)
    }
}