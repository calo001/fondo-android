package com.github.calo001.fondo.ui.main.fragment.search

import com.github.calo001.fondo.base.BasePhotoPresenterImpl
import com.github.calo001.fondo.model.Result

class SearchPresenterImpl(override val view: SearchViewContract) :
    BasePhotoPresenterImpl<SearchViewContract, SearchInteractorContract>(view),
    SearchPresenterContract {

    override val interactor: SearchInteractorContract = SearchInteractorImpl(this)

    override fun loadPhotos(query: String, page: Int) {
        interactor.loadPhotos(query, page)
    }

    override fun onPhotosSuccess(result: Result) {
        view.hideLoading()
        view.onLoadPhotosSuccess(result)
    }
}