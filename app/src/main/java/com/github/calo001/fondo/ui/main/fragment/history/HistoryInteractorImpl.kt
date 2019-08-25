package com.github.calo001.fondo.ui.main.fragment.history

import com.github.calo001.fondo.model.Photo
import com.github.calo001.fondo.repository.HistoryRepository
import com.github.calo001.fondo.repository.UnsplashRepository

class HistoryInteractorImpl (private val presenter: HistoryPresenterContract):
    HistoryInteractorContract {

    val repo = HistoryRepository()

    override fun loadPhotos(page: Int) {
        presenter.onPhotosSuccess(repo.getHistory(page = page))
    }

    override fun getDownloadLink(id: String) {
        UnsplashRepository.getDownloadLinkLocation(id)
            .subscribe({ response ->
                presenter.onDownloadLinkSuccess(response.url)
            }, { error ->
                presenter.onError(error.localizedMessage)
            })
    }

    override fun addToHistory(photo: Photo) {
        val repo = HistoryRepository()
        repo.saveToHistory(photo)
    }
}

