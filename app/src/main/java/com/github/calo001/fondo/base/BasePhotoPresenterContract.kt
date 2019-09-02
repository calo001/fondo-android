package com.github.calo001.fondo.base

import com.github.calo001.fondo.model.Photo
import com.github.calo001.fondo.network.ApiError

interface BasePhotoPresenterContract {
    fun onError(error: ApiError)
    fun getDownloadLink(id: String)
    fun onDownloadLinkSuccess(url: String)
}