package com.github.calo001.fondo.ui.main.fragment.search


import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.calo001.fondo.R
import com.github.calo001.fondo.adapter.PhotosAdapter
import com.github.calo001.fondo.adapter.PhotosAdapter.OnItemInteraction
import com.github.calo001.fondo.listener.InfiniteScrollListener
import com.github.calo001.fondo.listener.InfiniteScrollListener.OnLoadMoreListener
import com.github.calo001.fondo.model.Photo
import com.github.calo001.fondo.model.Result
import com.github.calo001.fondo.ui.detail.PhotoDetailActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.constraint
import kotlinx.android.synthetic.main.fragment_search.progress

class SearchFragment : Fragment(), SearchViewContract,
    OnItemInteraction, OnLoadMoreListener {
    private lateinit var adapter: PhotosAdapter

    private lateinit var scrollListener: InfiniteScrollListener
    private var page = FIRST_PAGE
    private var query = ""
    private val presenter: SearchPresenterContract =
        SearchPresenterImpl(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val linearLayoutManager = LinearLayoutManager(activity)
        scrollListener = InfiniteScrollListener(linearLayoutManager, this)
        rvSearchPhotos.layoutManager = linearLayoutManager
        rvSearchPhotos.addOnScrollListener(scrollListener)
        activity?.let {
            adapter = PhotosAdapter(mutableListOf(), it, this)
            adapter.addHeader(getString(R.string.search_header))
            rvSearchPhotos.adapter = adapter
        }
    }

    override fun onLoadPhotosSuccess(result: Result) {
        hideLoading()
        adapter.removeNullItem()
        adapter.updateHeader(query)
        adapter.addPage(result.results)
    }

    override fun showLoading() {
        progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress.visibility = View.GONE
        scrollListener.loading = false
    }

    override fun onError(error: String) {
        Snackbar.make(constraint, error, Snackbar.LENGTH_SHORT).show()
    }

    override fun onItemClick(view: View, item: Photo) {
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

    }

    override fun onLoadMore() {
        page++
        presenter.loadPhotos(query, page)
        adapter.addNullItem()
    }

    fun newSearchQuery(newQuery: String) {
        cleanData()
        query = newQuery
        presenter.loadPhotos(query, page)
    }

    fun cleanData() {
        page = 1
        adapter.clear()
    }

    fun scrollToUp() {
        rvSearchPhotos.smoothScrollToPosition(0)
    }

    companion object {
        const val TAG = "SearchFragment"
        const val FIRST_PAGE = 1

        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
