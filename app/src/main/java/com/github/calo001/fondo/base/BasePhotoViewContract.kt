package com.github.calo001.fondo.base

interface BasePhotoViewContract {
    fun showLoading()
    fun hideLoading()
    fun onError(error: String)
    fun onDownloadLinkSuccess(url: String)
}