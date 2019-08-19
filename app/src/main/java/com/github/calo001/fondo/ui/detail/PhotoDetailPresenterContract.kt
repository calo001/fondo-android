package com.github.calo001.fondo.ui.detail

interface PhotoDetailPresenterContract {
    fun getDownloadLink(id: String)
    fun onSuccess(image: String)
    fun onError(error: String)
}