package com.github.calo001.fondo.base.history

import com.github.calo001.fondo.model.Photo

interface BaseHistoryPresenterContract {
    fun addToHistory(photo: Photo)
    fun onHistorySuccess()
    fun onHistoryError()
}