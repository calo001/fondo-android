package com.github.calo001.fondo.base

import com.github.calo001.fondo.model.Photo

interface BasePhotoPresenterContract {
    fun onError(message: String)
    fun getDownloadLink(id: String)
    fun onDownloadLinkSuccess(url: String)
    fun addToHistory(photo: Photo)
}