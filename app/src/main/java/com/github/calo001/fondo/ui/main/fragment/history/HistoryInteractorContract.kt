package com.github.calo001.fondo.ui.main.fragment.history

import com.github.calo001.fondo.base.BasePhotoInteractorContract

interface HistoryInteractorContract : BasePhotoInteractorContract{
    fun loadPhotos(page: Int)
}