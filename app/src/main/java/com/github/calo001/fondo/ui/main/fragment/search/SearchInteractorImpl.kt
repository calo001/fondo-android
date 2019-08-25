package com.github.calo001.fondo.ui.main.fragment.search

import android.annotation.SuppressLint
import com.github.calo001.fondo.model.Photo
import com.github.calo001.fondo.repository.HistoryRepository
import com.github.calo001.fondo.repository.UnsplashRepository

class SearchInteractorImpl(private val presenter: SearchPresenterContract) : SearchInteractorContract {

    @SuppressLint("CheckResult")
    override fun loadPhotos(query: String, page: Int) {
        UnsplashRepository.getQueryPhotos(query, page)
            .subscribe({ result ->
                presenter.onPhotosSuccess(result)
            }, { error ->
                presenter.onError(error.localizedMessage.toString())
            })
    }

    @SuppressLint("CheckResult")
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