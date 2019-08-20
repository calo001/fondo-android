package com.github.calo001.fondo.ui.main.fragment.photo

import com.github.calo001.fondo.model.Photo

interface TodayPresenterContract {
    fun loadPhotos(page: Int)
    fun onPhotosSuccess(list: List<Photo>)
    fun onError(message: String)
}