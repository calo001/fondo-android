package com.github.calo001.fondo.ui.main.fragment.today

import android.annotation.SuppressLint
import com.github.calo001.fondo.model.Photo

class TodayPresenterImpl (private val view: TodayContract) :
    TodayPresenterContract {
    private val interactor: TodayInteractorContract =
        TodayInteractorImpl(this)

    @SuppressLint("CheckResult")
    override fun loadPhotos(page: Int) {
        interactor.loadPhotos(page)
    }

    override fun onPhotosSuccess(list: List<Photo>) {
        view.hideLoading()
        view.onloadPhotosSuccess(list)
    }

    override fun onError(message: String) {
        view.showError(message)
    }

}