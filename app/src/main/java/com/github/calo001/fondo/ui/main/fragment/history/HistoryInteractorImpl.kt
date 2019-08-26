package com.github.calo001.fondo.ui.main.fragment.history

import com.github.calo001.fondo.base.BasePhotoInteractorImpl
import com.github.calo001.fondo.repository.HistoryRepository

class HistoryInteractorImpl (override val presenter: HistoryPresenterContract):
    BasePhotoInteractorImpl<HistoryPresenterContract>(presenter),
    HistoryInteractorContract {

    private val repo = HistoryRepository()

    override fun loadPhotos(page: Int) {
        presenter.onPhotosSuccess(repo.getHistory(page = page))
    }
}

