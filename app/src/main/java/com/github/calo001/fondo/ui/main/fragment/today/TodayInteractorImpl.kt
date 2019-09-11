package com.github.calo001.fondo.ui.main.fragment.today

import android.annotation.SuppressLint
import com.github.calo001.fondo.base.BasePhotoInteractorImpl
import com.github.calo001.fondo.network.ApiError
import com.github.calo001.fondo.repository.UnsplashRepository

class TodayInteractorImpl (override val presenter: TodayPresenterContract):
    BasePhotoInteractorImpl<TodayPresenterContract>(presenter), TodayInteractorContract {

    @SuppressLint("CheckResult")
    override fun loadPhotos(page: Int) {
        UnsplashRepository.getTodayPhotos(page)
            .subscribe ( { list ->
                val listSorted = list.sortedWith(Comparator{ a, b ->
                    val c = a.width / a.height
                    val d = b.width / b.height
                    c - d
                })
                presenter.onPhotosSuccess(listSorted)
            }, { error ->
                val apiError = ApiError(error)
                presenter.onError(apiError)
            })
    }
}

