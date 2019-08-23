package com.github.calo001.fondo.ui.dialog.search

import com.github.calo001.fondo.model.SearchHistory

class SearchDialogPresenterImpl(val view: SearchDialogViewContract): SearchDialogPresenterContract {
    private val interactor: SearchDialogInteractorContract = SearchDialogInteractorImpl(this)

    override fun getHistory() {
        interactor.getHistory()
    }

    override fun onGetHistorySuccess(list: List<String>) {
        view.onSuccess(list)
    }

    override fun onError(error: String) {
        view.onError(error)
    }

    override fun addSearchHistoryItem(search: SearchHistory) {
        interactor.insert(search)
    }

    override fun deleteFromHistory(value: String) {
        interactor.delete(value)
    }
}