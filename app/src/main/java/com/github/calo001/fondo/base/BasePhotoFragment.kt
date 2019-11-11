package com.github.calo001.fondo.base

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.calo001.fondo.R
import com.github.calo001.fondo.adapter.PhotosAdapter
import com.github.calo001.fondo.adapter.PhotosAdapter.OnItemInteraction
import com.github.calo001.fondo.config.Constants.PERMISSION_REQUEST_CODE
import com.github.calo001.fondo.listener.InfiniteScrollListener
import com.github.calo001.fondo.listener.InfiniteScrollListener.OnLoadMoreListener
import com.github.calo001.fondo.manager.history.HistoryManager
import com.github.calo001.fondo.model.Photo
import com.github.calo001.fondo.network.ApiError
import com.github.calo001.fondo.service.NotificationService
import com.github.calo001.fondo.ui.detail.PhotoDetailActivity
import kotlinx.android.synthetic.main.fragment_photos.*
import kotlinx.android.synthetic.main.progress_layout.*

abstract class BasePhotoFragment<P : BasePhotoPresenterContract> : Fragment(), BasePhotoViewContract,
    OnItemInteraction, OnLoadMoreListener {

    private lateinit var mScrollListener: InfiniteScrollListener
    private var listener: OnFragmentInteractionListener? = null
    protected lateinit var mAdapter: PhotosAdapter
    protected var mPage = FIRST_PAGE
    private var mDownloadLink: String? = null
    private var mTmpPhoto: Photo? = null
    private val historyManager = HistoryManager()

    abstract val presenter: P

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManager = LinearLayoutManager(activity)
        mScrollListener = InfiniteScrollListener(layoutManager, this)
        rvPhotos.layoutManager = layoutManager
        rvPhotos.addOnScrollListener(mScrollListener)

        activity?.let {
            setupActivity(it)
        }
    }

    open fun setupActivity(activity: Activity) {
        mAdapter = PhotosAdapter(mutableListOf(), activity, this)
        setupHeader()
        rvPhotos.adapter = mAdapter

    }

    abstract fun setupHeader()

    override fun onLoadMore() {
        mPage++
        mAdapter.addNullItem()
        loadPhotos()
    }

    abstract fun loadPhotos()

    override fun showLoading() {
        progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress.visibility = View.GONE
        mScrollListener.loading = false
    }

    override fun onError(error: ApiError) {
        listener?.onFragmentDataError(error)
    }

    override fun onItemClick(item: Photo) {
        val intent = Intent(activity, PhotoDetailActivity::class.java)
        intent.putExtra(PhotoDetailActivity.EXTRA_OBJECT, item)
        startActivity(intent)
    }

    override fun onShareClick(photo: Photo) {
        activity?.let {
            val shareIntent = ShareCompat.IntentBuilder.from(activity)
                .setType("text/plain")
                .setSubject("${getString(R.string.share_photo_by)} ${photo.user.name}")
                .setText(
                    """${getString(R.string.photo_by)} ${photo.user.name} ${getString(R.string.on_unsplash)}
                |${photo.links.html}
                """.trimMargin())
                .intent
            it.packageManager?.let { startActivity(shareIntent) }
        }
    }

    override fun onSetWallClick(photo: Photo) {
        mTmpPhoto = photo
        presenter.getDownloadLink(photo.id)
        historyManager.addToHistory(photo)
    }

    fun scrollToUp() {
        rvPhotos.smoothScrollToPosition(0)
    }

    override fun onDownloadLinkSuccess(url: String) {
        mDownloadLink = url
        setAsWallpaper()
    }

    private fun setAsWallpaper() {
        if (checkPermission()) {
            startImageDownload()
        } else {
            requestPermission()
        }
    }

    private fun checkPermission(): Boolean {
        context?.let {
            val result = ContextCompat.checkSelfPermission(it, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            return result == PackageManager.PERMISSION_GRANTED
        }
        return false
    }

    private fun requestPermission() {
        activity?.let {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun startImageDownload() {
        val intent = Intent(activity, NotificationService::class.java)
        intent.putExtra(NotificationService.URL_EXTRA, mDownloadLink)
        intent.putExtra(NotificationService.ID_PHOTO_EXTRA, mTmpPhoto?.id)
        activity?.startService(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startImageDownload()
                } else {
                    Toast.makeText(context, resources.getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context mut implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
    interface OnFragmentInteractionListener {
        fun onFragmentDataError(error: ApiError)
    }

    companion object {
        const val FIRST_PAGE = 1
    }
}