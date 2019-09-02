package com.github.calo001.fondo.ui.detail

import com.github.calo001.fondo.network.ApiError

interface PhotoDetailPresenterContract {
    fun getDownloadLink(id: String)
    fun onDownloadLinkSuccess(image: String)
    fun onError(error: ApiError)
}
