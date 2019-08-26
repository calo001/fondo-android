package com.github.calo001.fondo.ui.main.fragment.search

import com.github.calo001.fondo.base.BasePhotoInteractorContract

interface SearchInteractorContract : BasePhotoInteractorContract {
    fun loadPhotos(query: String, page: Int)
}