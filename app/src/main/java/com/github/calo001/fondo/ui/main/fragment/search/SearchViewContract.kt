package com.github.calo001.fondo.ui.main.fragment.search

import com.github.calo001.fondo.model.Result

interface SearchViewContract {
    fun onLoadPhotosSuccess(result: Result)
    fun showLoading()
    fun hideLoading()
    fun onError(error: String)
}