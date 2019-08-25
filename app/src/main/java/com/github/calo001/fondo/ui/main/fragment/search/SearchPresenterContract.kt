package com.github.calo001.fondo.ui.main.fragment.search

import com.github.calo001.fondo.model.Photo
import com.github.calo001.fondo.model.Result

interface SearchPresenterContract {
    fun loadPhotos(query: String, page: Int) // Must change
    fun onPhotosSuccess(result: Result) // Must change
    fun onError(msg: String)
    fun getDownloadLink(id: String)
    fun onDownloadLinkSuccess(url: String)
    fun addToHistory(photo: Photo)
}