package com.github.calo001.fondo.ui.main.fragment.today

import android.app.Activity
import com.github.calo001.fondo.R
import com.github.calo001.fondo.base.BasePhotoFragment
import com.github.calo001.fondo.model.Photo

class TodayFragment : BasePhotoFragment<TodayPresenterContract>(), TodayViewContract {

    override val presenter: TodayPresenterContract = TodayPresenterImpl(this)

    override fun onloadPhotosSuccess(list: List<Photo>) {
        mAdapter.removeProgressItem()
        mAdapter.addPage(list)
    }

    override fun setupActivity(activity: Activity) {
        super.setupActivity(activity)
        presenter.loadPhotos(mPage)
    }

    override fun setupHeader() {
        mAdapter.addHeader(getString(R.string.today))
    }

    override fun loadPhotos() {
        presenter.loadPhotos(mPage)
    }

    companion object {
        const val TAG = "TodayFragment"

        @JvmStatic
        fun newInstance() = TodayFragment()
    }
}