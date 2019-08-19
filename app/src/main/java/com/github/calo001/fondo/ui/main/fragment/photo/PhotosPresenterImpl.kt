package com.github.calo001.fondo.ui.main.fragment.photo

import android.annotation.SuppressLint
import com.github.calo001.fondo.model.Photo

class PhotosPresenterImpl (private val view: PhotoViewContract) :
    PhotosPresenterContract {
    private val interactor: PhotosInteractorContract =
        PhotosInteractorImpl(this)

    @SuppressLint("CheckResult")
    override fun loadPhotos(page: Int) {
        interactor.loadPhotos(page)
    }

    override fun onPhotosSuccess(list: List<Photo>) {
        view.hideLoading()
        view.onloadPhotosSuccess(list)
    }

    override fun onError(message: String) {
        view.showError(message)
    }

}