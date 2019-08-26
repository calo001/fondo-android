package com.github.calo001.fondo.ui.main.fragment.today

import com.github.calo001.fondo.base.BasePhotoPresenterContract
import com.github.calo001.fondo.model.Photo

interface TodayPresenterContract: BasePhotoPresenterContract {
    fun loadPhotos(page: Int) // Must change
    fun onPhotosSuccess(list: List<Photo>) // Must change
}