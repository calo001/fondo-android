package com.github.calo001.fondo.repository

import com.github.calo001.fondo.model.Photo
import com.github.calo001.fondo.util.FondoJsonManager

class HistoryRepository {
    private val jsonManager = FondoJsonManager()

    fun getHistory(page: Int): List<Photo> {
        return jsonManager.getHistory(page)
    }

    fun saveToHistory(photo: Photo): Boolean {
        return jsonManager.addToHistory(photo)
    }
}