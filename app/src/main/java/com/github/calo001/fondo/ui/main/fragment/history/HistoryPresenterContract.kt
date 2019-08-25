package com.github.calo001.fondo.ui.main.fragment.history

import com.github.calo001.fondo.model.Photo

interface HistoryPresenterContract {
    fun loadPhotos(page: Int) // Must change
    fun onPhotosSuccess(list: List<Photo>) // Must change
    fun onError(message: String)
    fun getDownloadLink(id: String)
    fun onDownloadLinkSuccess(url: String)
    fun addToHistory(photo: Photo)
}