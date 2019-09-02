package com.github.calo001.fondo.manager.history

import com.github.calo001.fondo.model.Photo

interface HistoryPresenterContract {
    fun addToHistory(photo: Photo)
    fun onHistorySuccess()
    fun onHistoryError()
}