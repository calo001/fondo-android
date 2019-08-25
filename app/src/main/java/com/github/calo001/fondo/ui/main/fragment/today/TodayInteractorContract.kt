package com.github.calo001.fondo.ui.main.fragment.today

import com.github.calo001.fondo.model.Photo

interface TodayInteractorContract {
    fun loadPhotos(page: Int) // Must change
    fun getDownloadLink(id: String)
    fun addToHistory(photo: Photo)
}