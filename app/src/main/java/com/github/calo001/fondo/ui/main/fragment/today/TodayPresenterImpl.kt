package com.github.calo001.fondo.ui.main.fragment.today

import com.github.calo001.fondo.base.BasePhotoPresenterImpl
import com.github.calo001.fondo.model.Photo

class TodayPresenterImpl (override val view: TodayViewContract) :
    BasePhotoPresenterImpl<TodayViewContract, TodayInteractorContract>(view),
    TodayPresenterContract {

    override val interactor: TodayInteractorContract = TodayInteractorImpl(this) // Must change

    override fun loadPhotos(page: Int) {
        interactor.loadPhotos(page)
    }

    override fun onPhotosSuccess(list: List<Photo>) {
        view.hideLoading()
        view.onLoadPhotosSuccess(list)
    }
}