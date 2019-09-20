package com.github.calo001.fondo.ui.dialog.search

import android.annotation.SuppressLint
import com.github.calo001.fondo.Fondo
import com.github.calo001.fondo.db.AppDBProvider
import com.github.calo001.fondo.model.SearchHistory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchDialogInteractorImpl(private val presenter: SearchDialogPresenterContract) : SearchDialogInteractorContract {
    val db = AppDBProvider.getInstance(Fondo.getInstance())

    @SuppressLint("CheckResult")
    override fun getHistory() {
        db.searchHistoryDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                presenter.onGetHistorySuccess(list)
            }, {
                it.message?.let { msg -> presenter.onError(msg) }
            })
    }

    override fun insert(search: SearchHistory) {
        db.searchHistoryDao().insert(search)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override fun update(search: SearchHistory) {
        db.searchHistoryDao().update(search)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override fun delete(search: String) {
        db.searchHistoryDao().delete(search)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }


}
