package com.github.calo001.fondo.ui.dialog.search

import com.github.calo001.fondo.model.SearchHistory

interface SearchDialogPresenterContract {
    fun getHistory()
    fun onGetHistorySuccess(list: List<String>)
    fun onError(error: String)
    fun addSearchHistoryItem(search: SearchHistory)
    fun deleteFromHistory(value: String)
}