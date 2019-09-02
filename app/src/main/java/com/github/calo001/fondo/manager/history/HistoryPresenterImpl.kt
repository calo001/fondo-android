package com.github.calo001.fondo.manager.history

import com.github.calo001.fondo.model.Photo

class HistoryPresenterImpl (val manager: HistoryContract): HistoryPresenterContract {

    private val interactor = HistoryInteractorImpl(this)

    override fun addToHistory(photo: Photo) {
        interactor.addToHistory(photo)
    }

    override fun onHistorySuccess() {
        manager.onHistorySuccess()
    }

    override fun onHistoryError() {
        manager.onHistoryError()
    }
}