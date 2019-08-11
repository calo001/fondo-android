package io.codyffly.fondo.ui.main.fragment.photo

import io.codyffly.fondo.model.Photo

interface PhotoViewContract {
    fun onloadPhotosSuccess(list: List<Photo>)
    fun showLoading()
    fun hideLoading()
    fun showError(error: String)
}