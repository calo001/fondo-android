package io.codyffly.fondo.dialog

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.transition.*
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import io.codyffly.fondo.R
import kotlinx.android.synthetic.main.layout_search.*
import kotlinx.android.synthetic.main.layout_search.view.*

class SearchDialogFragment: DialogFragment() {
    private var listener: OnSearchListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context)
                .inflateTransition(android.R.transition.explode)
            enterTransition = ArcMotion()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.layout_search, container,false)

        view.toolbarSearch.setNavigationIcon(R.drawable.ic_back)
        view.toolbarSearch.setNavigationOnClickListener { dismiss() }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { listener?.onSearch(it) }
                this@SearchDialogFragment.dismiss()
                return false
            }
        })
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            it.window?.setLayout(width, height)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSearchListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnSearchListner")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        const val TAG = "SearchDialogFragment"
    }

    interface OnSearchListener {
        fun onSearch(term: String)
    }
}