package com.github.calo001.fondo.ui.detail

interface PhotoDetailViewContract {
    fun onSuccess(image: String)
    fun onError(error: String)
}