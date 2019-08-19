package com.github.calo001.fondo.ui.main.fragment.photo

import com.github.calo001.fondo.model.Photo

interface PhotoViewContract {
    fun onloadPhotosSuccess(list: List<Photo>)
    fun showLoading()
    fun hideLoading()
    fun showError(error: String)
}