package com.github.calo001.fondo.ui.main.fragment.search

interface SearchInteractorContract {
    fun loadPhotos(query: String, page: Int)
}