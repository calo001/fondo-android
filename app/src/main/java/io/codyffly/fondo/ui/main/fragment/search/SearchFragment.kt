package io.codyffly.fondo.ui.main.fragment.search


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.codyffly.fondo.R
import io.codyffly.fondo.adapter.PhotosAdapter
import io.codyffly.fondo.model.Photo
import io.codyffly.fondo.model.Result
import kotlinx.android.synthetic.main.fragment_search.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SearchFragment : androidx.fragment.app.Fragment() {
    var photos: MutableList<Photo?> = mutableListOf()
    var isLoading = false
    var query = "cat"
    var page = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvSearchPhotos.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        activity.let {
            //rvSearchPhotos.adapter = PhotosAdapter(photos, activity!!.application,)
            initScrollListener()
            setupPhotoList()
        }
    }

    @SuppressLint("CheckResult")
    private fun setupPhotoList() {
        if (!query.trim().isEmpty()) {

        }
    }

    fun onPhotosListError(error: Throwable) {
        clearLoading()
        Toast.makeText(this.context, "ERROR CUSTOM: " + error.localizedMessage, Toast.LENGTH_LONG).show()
    }

    private fun onPhotoListSucces(result: Result) {
        clearLoading()
        photos.addAll(result.results)
        rvSearchPhotos.adapter?.notifyDataSetChanged()
    }

    fun updateQuerySearch(query: String) {
        clearList()
        setupPhotoList()
    }

    private fun clearLoading() {
        isLoading = false;
        if (photos.size > 0) {
            photos.removeAt(photos.size.minus(1))
        }
    }

    private fun clearList() {
        photos.clear()
        rvSearchPhotos.adapter?.notifyDataSetChanged()
    }

    private fun initScrollListener() {
        rvSearchPhotos.addOnScrollListener(object: androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager
                if (!isLoading) {
                    if (layoutManager is androidx.recyclerview.widget.LinearLayoutManager &&
                        layoutManager.findLastCompletelyVisibleItemPosition() == photos.size.minus(1)) {
                        page++
                        isLoading = true
                        loadMore()
                    }
                }
            }
        })
    }

    private fun loadMore() {
        photos.add(null)
        rvSearchPhotos.adapter?.notifyItemInserted(photos.size.minus(1))
        setupPhotoList()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
