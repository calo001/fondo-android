package io.codyffly.fondo.ui.detail

interface PhotoDetailViewContract {
    fun onSuccess(image: String)
    fun onError(error: String)
}