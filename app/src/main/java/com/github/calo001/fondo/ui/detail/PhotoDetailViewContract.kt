package com.github.calo001.fondo.ui.detail

import com.github.calo001.fondo.network.ApiError

interface PhotoDetailViewContract {
    fun onSuccess(image: String)
    fun onError(error: ApiError)
}