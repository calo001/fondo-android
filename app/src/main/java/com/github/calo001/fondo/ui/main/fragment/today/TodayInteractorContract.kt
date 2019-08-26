package com.github.calo001.fondo.ui.main.fragment.today

import com.github.calo001.fondo.base.BasePhotoInteractorContract

interface TodayInteractorContract : BasePhotoInteractorContract {
    fun loadPhotos(page: Int)
}