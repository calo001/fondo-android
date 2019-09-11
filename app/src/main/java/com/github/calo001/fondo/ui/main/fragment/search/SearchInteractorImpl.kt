package com.github.calo001.fondo.ui.main.fragment.search

import android.annotation.SuppressLint
import com.github.calo001.fondo.base.BasePhotoInteractorImpl
import com.github.calo001.fondo.model.Photo
import com.github.calo001.fondo.model.Result
import com.github.calo001.fondo.network.ApiError
import com.github.calo001.fondo.repository.UnsplashRepository

class SearchInteractorImpl(override val presenter: SearchPresenterContract) :
    BasePhotoInteractorImpl<SearchPresenterContract>(presenter), SearchInteractorContract {

    @SuppressLint("CheckResult")
    override fun loadPhotos(query: String, page: Int) {
        UnsplashRepository.getQueryPhotos(query, page)
            .subscribe({ result ->
                val listSorted = result.results.sortedWith(Comparator{a, b ->
                    val c = a.width / a.height
                    val d = b.width / b.height
                    c - d
                })
                presenter.onPhotosSuccess(Result(listSorted, result.total))
            }, { error ->
                val apiError = ApiError(error)
                presenter.onError(apiError)
            })
    }
}