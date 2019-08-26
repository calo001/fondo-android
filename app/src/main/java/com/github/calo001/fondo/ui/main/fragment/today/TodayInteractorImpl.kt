package com.github.calo001.fondo.ui.main.fragment.today

import android.annotation.SuppressLint
import com.github.calo001.fondo.base.BasePhotoInteractorImpl
import com.github.calo001.fondo.repository.UnsplashRepository

class TodayInteractorImpl (override val presenter: TodayPresenterContract):
    BasePhotoInteractorImpl<TodayPresenterContract>(presenter), TodayInteractorContract {

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

