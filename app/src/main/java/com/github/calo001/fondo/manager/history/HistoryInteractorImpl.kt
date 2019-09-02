package com.github.calo001.fondo.manager.history

import com.github.calo001.fondo.model.Photo
import com.github.calo001.fondo.repository.HistoryRepository

class HistoryInteractorImpl(val presenter: HistoryPresenterContract) : HistoryInteractorContract {

    override fun addToHistory(photo: Photo) {
        val repo = HistoryRepository()
        val result = repo.saveToHistory(photo)
        if (result) {
            presenter.onHistorySuccess()
        } else {
            presenter.onHistoryError()
        }
    }
}