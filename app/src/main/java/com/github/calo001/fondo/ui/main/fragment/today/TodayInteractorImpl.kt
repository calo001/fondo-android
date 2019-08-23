package com.github.calo001.fondo.ui.main.fragment.today

import android.annotation.SuppressLint
import com.github.calo001.fondo.repository.UnsplashRepository

class TodayInteractorImpl (private val presenter: TodayPresenterContract):
    TodayInteractorContract {

    @SuppressLint("CheckResult")
    override fun loadPhotos(page: Int) {
        UnsplashRepository.getTodayPhotos(page)
            .subscribe ( { list ->
                presenter.onPhotosSuccess(list)
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
}

