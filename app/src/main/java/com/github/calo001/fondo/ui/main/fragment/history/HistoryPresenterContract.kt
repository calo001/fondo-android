package com.github.calo001.fondo.ui.main.fragment.history

import com.github.calo001.fondo.base.BasePhotoPresenterContract
import com.github.calo001.fondo.model.Photo

interface HistoryPresenterContract : BasePhotoPresenterContract{
    fun loadPhotos(page: Int)
    fun onPhotosSuccess(list: List<Photo>)
}