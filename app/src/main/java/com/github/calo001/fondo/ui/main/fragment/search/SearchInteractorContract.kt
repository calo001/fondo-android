package com.github.calo001.fondo.ui.main.fragment.search

import com.github.calo001.fondo.model.Photo

interface SearchInteractorContract {
    fun loadPhotos(query: String, page: Int) // Must change
    fun getDownloadLink(id: String)
    fun addToHistory(photo: Photo)
}