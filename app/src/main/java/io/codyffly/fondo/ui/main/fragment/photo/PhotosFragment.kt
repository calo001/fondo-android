package io.codyffly.fondo.ui.main.fragment.photo

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.codyffly.fondo.R
import io.codyffly.fondo.adapter.PhotosAdapter
import io.codyffly.fondo.config.Constants
import io.codyffly.fondo.model.Photo
import io.codyffly.fondo.ui.detail.PhotoDetailActivity
import kotlinx.android.synthetic.main.fragment_photos.*

class PhotosFragment : PhotoViewContract, OnItemInteraction, Fragment(){
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var adapter: PhotosAdapter

    private val presenter: PhotosPresenterContract = PhotosPresenterImpl(this)
    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvTodayPhotos.layoutManager = LinearLayoutManager(activity)

        activity?.let {
            adapter = PhotosAdapter(mutableListOf(), it, this)
            rvTodayPhotos.adapter = adapter
            initScrollListener()
            presenter.loadPhotos(page)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun initScrollListener() {
        rvTodayPhotos.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager
                if (layoutManager is LinearLayoutManager &&
                    layoutManager.findLastCompletelyVisibleItemPosition() == (adapter.itemCount.minus(1))) {
                    showLoading()
                    presenter.loadPhotos(page++)
                }

            }
        })
    }

    override fun onloadPhotosSuccess(list: List<Photo>) {
        hideLoading()
        adapter.addPage(list)
    }

    override fun showLoading() {
        progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress.visibility = View.GONE
    }

    override fun showError(error: String) {
        Snackbar.make(constraint, error, Snackbar.LENGTH_SHORT).show()
    }

    override fun onItemInteraction(view: View, item: Photo) {
        val intent = Intent(activity, PhotoDetailActivity::class.java)
//        intent.putExtra(Constants.EXTRA_URI_IMAGE_VIEW, item.urls.regular)
//        intent.putExtra(Constants.EXTRA_USER_NAME, item.user.name)
//        intent.putExtra(Constants.EXTRA_USER_BIO, item.user.bio)
//        intent.putExtra(Constants.EXTRA_CREATED_AT, item.created_at)
//        intent.putExtra(Constants.EXTRA_WIDTH, item.width)
//        intent.putExtra(Constants.EXTRA_HEIGHT, item.height)
//        intent.putExtra(Constants.EXTRA_DESCRIPTION, item.description)
//        intent.putExtra(Constants.EXTRA_COLOR, item.color)
//        intent.putExtra(Constants.EXTRA_DOWNLOAD_LOCATION, item.links.download_location)

        intent.putExtra("object", item)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptions
                .makeSceneTransitionAnimation(activity, view, resources.getString(R.string.photo_transition))
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        fun newInstance() = PhotosFragment()
    }
}

interface OnItemInteraction {
    fun onItemInteraction(view: View, item: Photo)
}
