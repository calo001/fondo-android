package com.github.calo001.fondo.ui.main.fragment.history

import com.github.calo001.fondo.base.BasePhotoPresenterImpl
import com.github.calo001.fondo.model.Photo

class HistoryPresenterImpl (override val view: HistoryViewContract) : BasePhotoPresenterImpl<HistoryViewContract, HistoryInteractorContract>(view),
    HistoryPresenterContract {

    override val interactor: HistoryInteractorContract = HistoryInteractorImpl(this)

    override fun loadPhotos(page: Int) {
        interactor.loadPhotos(page)
    }

    override fun onPhotosSuccess(list: List<Photo>) {
        view.hideLoading()
        view.onLoadPhotosSuccess(list)
    }
}