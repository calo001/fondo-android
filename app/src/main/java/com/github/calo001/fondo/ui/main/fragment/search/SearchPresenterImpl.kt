package com.github.calo001.fondo.ui.main.fragment.search

import com.github.calo001.fondo.model.Photo
import com.github.calo001.fondo.model.Result

class SearchPresenterImpl(val view: SearchViewContract) :
    SearchPresenterContract {
    private val interactor: SearchInteractorContract =
        SearchInteractorImpl(this) // Must change

    override fun loadPhotos(query: String, page: Int) {
        interactor.loadPhotos(query, page)
    } // Must change

    override fun onPhotosSuccess(result: Result) {
        view.hideLoading()
        view.onLoadPhotosSuccess(result)
    } // Must change

    override fun onError(msg: String) {
        view.onError(msg)
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