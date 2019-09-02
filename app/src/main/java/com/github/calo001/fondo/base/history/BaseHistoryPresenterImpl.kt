package com.github.calo001.fondo.base.history

import com.github.calo001.fondo.model.Photo

abstract class BaseHistoryPresenterImpl<V: BaseHistoryContract, I: BaseHistoryInteractorContract>
    (val view: V): BaseHistoryPresenterContract {

    abstract val interactor: I

    override fun addToHistory(photo: Photo) {
        interactor.addToHistory(photo)
    }

    override fun onHistorySuccess() {
        view.onHistorySuccess()
    }

    override fun onHistoryError() {
        view.onHistoryError()
    }
}