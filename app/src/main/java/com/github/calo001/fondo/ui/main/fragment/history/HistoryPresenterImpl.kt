package com.github.calo001.fondo.ui.main.fragment.history

import com.github.calo001.fondo.model.Photo

class HistoryPresenterImpl (private val view: HistoryViewContract) :
    HistoryPresenterContract {
    private val interactor: HistoryInteractorContract =
        HistoryInteractorImpl(this) // Must change

    override fun loadPhotos(page: Int) {
        interactor.loadPhotos(page)
    } // Must change

    override fun onPhotosSuccess(list: List<Photo>) {
        view.hideLoading()
        view.onloadPhotosSuccess(list)
    } // Must change

    override fun onError(message: String) {
        view.showError(message)
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