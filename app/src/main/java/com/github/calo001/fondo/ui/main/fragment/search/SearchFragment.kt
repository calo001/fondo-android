package com.github.calo001.fondo.ui.main.fragment.search

import com.github.calo001.fondo.R
import com.github.calo001.fondo.base.BasePhotoFragment
import com.github.calo001.fondo.model.Result
import com.github.calo001.fondo.network.ApiError

class SearchFragment : BasePhotoFragment<SearchPresenterContract>(), SearchViewContract {
    override val presenter: SearchPresenterContract = SearchPresenterImpl(this)
    private var query = ""

    override fun onLoadPhotosSuccess(result: Result) {
        if (result.results.isEmpty() and (mPage == FIRST_PAGE)) {
            onError(ApiError(204, resources.getString(R.string.empty_search)))
        } else {
            mAdapter.removeProgressItem()
            mAdapter.addPage(result.results)
        }
    }

    override fun setupHeader() {
        mAdapter.addHeader(getString(R.string.search_header))
    }

    override fun loadPhotos() {
        presenter.loadPhotos(query, mPage)
    }

    fun newSearchQuery(newQuery: String) {
        cleanData()
        query = newQuery
        presenter.loadPhotos(query, mPage)
        mAdapter.updateHeader(query)
    }

    fun cleanData() {
        mPage = 1
        mAdapter.clear()
        showLoading()
    }

    companion object {
        const val TAG = "SearchFragment"

        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
