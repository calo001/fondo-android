package com.github.calo001.fondo.repository

import com.github.calo001.fondo.manager.json.FondoJsonManager
import com.github.calo001.fondo.model.Photo

class HistoryRepository {
    private val jsonManager = FondoJsonManager()

    fun getHistory(page: Int): List<Photo> {
        return jsonManager.getHistory(page)
    }

    fun saveToHistory(photo: Photo): Boolean {
        return jsonManager.addToHistory(photo)
    }
}