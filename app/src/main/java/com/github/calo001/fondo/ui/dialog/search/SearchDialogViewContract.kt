package com.github.calo001.fondo.ui.dialog.search

interface SearchDialogViewContract {
    fun onSuccess(list: List<String>)
    fun onError(error: String)
}