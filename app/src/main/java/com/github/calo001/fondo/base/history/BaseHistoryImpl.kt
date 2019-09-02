package com.github.calo001.fondo.base.history

import com.github.calo001.fondo.model.Photo

abstract class BaseHistoryImpl<P: BaseHistoryPresenterContract> : BaseHistoryContract {
    abstract val historyPresenter: P

    fun addToHistory(photo: Photo) {
        historyPresenter.addToHistory(photo)
    }
}