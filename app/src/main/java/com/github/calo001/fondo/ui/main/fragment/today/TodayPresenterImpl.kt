package com.github.calo001.fondo.ui.main.fragment.today

import com.github.calo001.fondo.base.BasePhotoPresenter
import com.github.calo001.fondo.model.Photo

class TodayPresenterImpl (override val view: TodayViewContract) :
    BasePhotoPresenter<TodayViewContract, TodayInteractorContract>(view),
    TodayPresenterContract {

    override val interactor: TodayInteractorContract = TodayInteractorImpl(this) // Must change

    override fun loadPhotos(page: Int) {
        interactor.loadPhotos(page)
    }

    override fun onPhotosSuccess(list: List<Photo>) {
        view.hideLoading()
        view.onloadPhotosSuccess(list)
    }
}