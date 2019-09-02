package com.github.calo001.fondo.base.history

import com.github.calo001.fondo.model.Photo
import com.github.calo001.fondo.repository.HistoryRepository

abstract class BaseHistoryInteractorImpl<P: BaseHistoryPresenterContract> (val presenter: P) : BaseHistoryInteractorContract {
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