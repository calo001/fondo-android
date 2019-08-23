package com.github.calo001.fondo.ui.dialog.search

import com.github.calo001.fondo.model.SearchHistory

interface SearchDialogInteractorContract {
    fun getHistory()
    fun insert(search: SearchHistory)
    fun update(search: SearchHistory)
    fun delete(search: String)
}