package io.codyffly.fondo.ui.main.fragment.photo

import android.annotation.SuppressLint
import android.widget.Toast
import io.codyffly.fondo.model.Photo

class PhotosPresenterImpl (private val view: PhotoViewContract) : PhotosPresenterContract {
    private val interactor: PhotosInteractorContract = PhotosInteractorImpl(this)

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