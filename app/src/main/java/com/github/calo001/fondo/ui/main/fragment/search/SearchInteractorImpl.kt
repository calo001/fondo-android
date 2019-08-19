package com.github.calo001.fondo.ui.main.fragment.search

import android.annotation.SuppressLint
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

}