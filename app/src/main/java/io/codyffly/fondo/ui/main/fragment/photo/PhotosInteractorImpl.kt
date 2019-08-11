package io.codyffly.fondo.ui.main.fragment.photo

import android.annotation.SuppressLint
import io.codyffly.fondo.repository.UnsplashRepository

class PhotosInteractorImpl (private val presenter: PhotosPresenterContract): PhotosInteractorContract {

    @SuppressLint("CheckResult")
    override fun loadPhotos(page: Int) {
        UnsplashRepository.getDailyPhotos(page)
            .subscribe ( { list ->
                presenter.onPhotosSuccess(list)
            }, { error ->
                presenter.onError(error.localizedMessage.toString())
            })
    }
}

