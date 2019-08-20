package com.github.calo001.fondo.ui.main.fragment.photo

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
}

