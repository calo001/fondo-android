package com.github.calo001.fondo.ui.main.fragment.search

import com.github.calo001.fondo.model.Result

interface SearchPresenterContract {
    fun loadPhotos(query: String, page: Int)
    fun onPhotosSuccess(result: Result)
    fun onError(msg: String)
    fun getDownloadLink(id: String)
    fun onDownloadLinkSuccess(url: String)
}