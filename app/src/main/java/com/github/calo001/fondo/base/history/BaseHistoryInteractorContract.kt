package com.github.calo001.fondo.base.history

import com.github.calo001.fondo.model.Photo

interface BaseHistoryInteractorContract {
    fun addToHistory(photo: Photo)
}