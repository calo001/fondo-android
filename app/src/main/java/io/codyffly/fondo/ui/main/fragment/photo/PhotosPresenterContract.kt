package io.codyffly.fondo.ui.main.fragment.photo

import io.codyffly.fondo.model.Photo

interface PhotosPresenterContract {
    fun loadPhotos(page: Int)
    fun onPhotosSuccess(list: List<Photo>)
    fun onError(message: String)
}