package com.github.calo001.fondo.ui.main.fragment.history

import com.github.calo001.fondo.model.Photo

interface HistoryInteractorContract {
    fun loadPhotos(page: Int) // Must change
    fun getDownloadLink(id: String)
    fun addToHistory(photo: Photo)
}