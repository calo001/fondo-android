package io.codyffly.fondo.ui.main.fragment.photo

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.codyffly.fondo.R
import io.codyffly.fondo.adapter.PhotosAdapter
import io.codyffly.fondo.listener.InfiniteScrollListener
import io.codyffly.fondo.listener.InfiniteScrollListener.OnLoadMoreListener
import io.codyffly.fondo.model.Photo
import io.codyffly.fondo.ui.detail.PhotoDetailActivity
import kotlinx.android.synthetic.main.fragment_photos.*

class PhotosFragment : Fragment(), PhotoViewContract, OnItemInteraction, OnLoadMoreListener {
    private lateinit var adapter: PhotosAdapter
    private lateinit var scrollListener: InfiniteScrollListener

    private val presenter: PhotosPresenterContract = PhotosPresenterImpl(this)
    var page = FIRST_PAGE

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val linearLayoutManager = LinearLayoutManager(activity)
        scrollListener = InfiniteScrollListener(linearLayoutManager, this)
        rvTodayPhotos.layoutManager = linearLayoutManager
        rvTodayPhotos.addOnScrollListener(scrollListener)

        activity?.let {
            adapter = PhotosAdapter(mutableListOf(), it, this)
            adapter.addHeader(resources.getString(R.string.today))
            rvTodayPhotos.adapter = adapter
            presenter.loadPhotos(page)
        }
    }

    override fun onloadPhotosSuccess(list: List<Photo>) {
        hideLoading()
        adapter.removeNullItem()
        adapter.addPage(list)
    }

    override fun onLoadMore() {
        page++
        presenter.loadPhotos(page)
        adapter.addNullItem()
    }

    override fun showLoading() {
        progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress.visibility = View.GONE
        scrollListener.loading = false
    }

    override fun showError(error: String) {
        Snackbar.make(constraint, error, Snackbar.LENGTH_SHORT).show()
    }

    override fun onItemInteraction(view: View, item: Photo) {
        val intent = Intent(activity, PhotoDetailActivity::class.java)
        intent.putExtra(PhotoDetailActivity.EXTRA_OBJECT, item)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptions
                .makeSceneTransitionAnimation(activity, view, resources.getString(R.string.photo_transition))
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }

    fun scrollToUp() {
        rvTodayPhotos.smoothScrollToPosition(0)
    }

    companion object {
        const val TAG = "PhotosFragment"
        const val FIRST_PAGE = 1

        @JvmStatic
        fun newInstance() = PhotosFragment()
    }
}

interface OnItemInteraction {
    fun onItemInteraction(view: View, item: Photo)
}
