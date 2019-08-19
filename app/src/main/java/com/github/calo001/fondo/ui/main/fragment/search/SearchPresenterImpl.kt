package com.github.calo001.fondo.ui.main.fragment.search

import com.github.calo001.fondo.model.Result

class SearchPresenterImpl(val view: SearchViewContract) : SearchPresenterContract {
    private val interactor: SearchInteractorContract =
        SearchInteractorImpl(this)

    override fun loadPhotos(query: String, page: Int) {
        interactor.loadPhotos(query, page)
    }

    override fun onPhotosSuccess(result: Result) {
        view.onLoadPhotosSuccess(result)
    }

    override fun onError(msg: String) {
        view.onError(msg)
    }

}