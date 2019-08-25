package com.github.calo001.fondo.ui.main.fragment.history

import com.github.calo001.fondo.model.Photo

interface HistoryViewContract {
    fun onloadPhotosSuccess(list: List<Photo>)
    fun showLoading()
    fun hideLoading()
    fun showError(error: String)
    fun onDownloadLinkSuccess(url: String)
}