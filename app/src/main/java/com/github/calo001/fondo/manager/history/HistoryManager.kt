package com.github.calo001.fondo.manager.history

import android.util.Log
import com.github.calo001.fondo.model.Photo

class HistoryManager : HistoryContract {
    private val historyPresenter: HistoryPresenterContract = HistoryPresenterImpl(this)

    fun addToHistory(photo: Photo) {
        historyPresenter.addToHistory(photo)
    }

    override fun onHistorySuccess() {
        Log.d(TAG, "History success")
    }

    override fun onHistoryError() {
        Log.d(TAG, "History error")
    }

    companion object {
        const val TAG = "HistoryManager"
    }
}