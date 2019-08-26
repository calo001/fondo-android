package com.github.calo001.fondo.ui.main.fragment.history

import com.github.calo001.fondo.R
import com.github.calo001.fondo.base.BasePhotoFragment
import com.github.calo001.fondo.model.Photo

class HistoryFragment : BasePhotoFragment<HistoryPresenterContract>(), HistoryViewContract {
    override val presenter: HistoryPresenterContract = HistoryPresenterImpl(this)

    override fun onloadPhotosSuccess(list: List<Photo>) {
        mAdapter.removeProgressItem()
        mAdapter.addPage(list)
    }

    override fun setupHeader() {
        mAdapter.addHeader(getString(R.string.history))
    }

    override fun loadPhotos() {
        presenter.loadPhotos(mPage)
    }

    fun reloadHistory() {
        cleanData()
        presenter.loadPhotos(mPage)
    }

    private fun cleanData() {
        mPage = 1
        mAdapter.clearOnlyData()
    }

    companion object {
        const val TAG = "HistoryFragment"

        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}