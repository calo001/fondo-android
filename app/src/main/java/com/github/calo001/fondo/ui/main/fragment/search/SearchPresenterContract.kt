package com.github.calo001.fondo.ui.main.fragment.search

import com.github.calo001.fondo.base.BasePhotoPresenterContract
import com.github.calo001.fondo.model.Result

interface SearchPresenterContract: BasePhotoPresenterContract {
    fun loadPhotos(query: String, page: Int)
    fun onPhotosSuccess(result: Result)
}