package com.github.calo001.fondo.ui.main.fragment.today

import com.github.calo001.fondo.model.Photo

interface TodayViewContract {
    fun onloadPhotosSuccess(list: List<Photo>)
    fun showLoading()
    fun hideLoading()
    fun showError(error: String)
    fun onDownloadLinkSuccess(url: String)
}