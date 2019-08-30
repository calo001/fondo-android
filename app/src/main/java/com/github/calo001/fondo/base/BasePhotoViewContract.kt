package com.github.calo001.fondo.base

import com.github.calo001.fondo.network.ApiError

interface BasePhotoViewContract {
    fun showLoading()
    fun hideLoading()
    fun onError(error: ApiError)
    fun onDownloadLinkSuccess(url: String)
}