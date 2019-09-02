package com.github.calo001.fondo.manager.history

import com.github.calo001.fondo.model.Photo

interface HistoryInteractorContract {
    fun addToHistory(photo: Photo)
}