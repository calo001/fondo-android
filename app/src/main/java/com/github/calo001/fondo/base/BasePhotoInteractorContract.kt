package com.github.calo001.fondo.base

import com.github.calo001.fondo.model.Photo

interface BasePhotoInteractorContract {
    fun getDownloadLink(id: String)
    fun addToHistory(photo: Photo)
}