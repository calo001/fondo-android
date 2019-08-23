package com.github.calo001.fondo.ui.main.fragment.today

import com.github.calo001.fondo.model.Photo

interface TodayPresenterContract {
    fun loadPhotos(page: Int)
    fun onPhotosSuccess(list: List<Photo>)
    fun onError(message: String)
    fun getDownloadLink(id: String)
    fun onDownloadLinkSuccess(url: String)
}