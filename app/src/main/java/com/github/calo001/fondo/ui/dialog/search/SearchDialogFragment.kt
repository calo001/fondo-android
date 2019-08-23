package com.github.calo001.fondo.ui.dialog.search

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.transition.*
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.calo001.fondo.R
import com.github.calo001.fondo.adapter.SearchHistoryAdapter
import com.github.calo001.fondo.adapter.SearchHistoryAdapter.OnSearchHistoryClickListener
import com.github.calo001.fondo.model.SearchHistory
import kotlinx.android.synthetic.main.layout_search.*
import kotlinx.android.synthetic.main.layout_search.view.*

class SearchDialogFragment: DialogFragment(), SearchDialogViewContract, OnSearchHistoryClickListener {
    private var listener: OnSearchListener? = null

    private var presenter: SearchDialogPresenterContract = SearchDialogPresenterImpl(this)
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: SearchHistoryAdapter
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
        recycler = view.rvSearchHistory
        presenter.getHistory()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { adapter.filter(it) }
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { sendForSearch(it) }
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

    override fun onSearchHistoryClick(value: String) {
        sendForSearch(value)
    }

    override fun onDeleteItem(item: String) {
        presenter.deleteFromHistory(item)
    }

    private fun initRecycler(list: List<String>) {
        activity?.let {
            adapter = SearchHistoryAdapter(list as MutableList<String>, it, this)
            recycler.layoutManager = LinearLayoutManager(it)
            recycler.adapter = adapter
        }
    }

    override fun onSuccess(list: List<String>) {
        initRecycler(list)
    }

    override fun onError(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
    }

    fun sendForSearch(value: String) {
        listener?.onSearch(value)
        presenter.addSearchHistoryItem(SearchHistory(value = value))
        dismiss()
    }

    companion object {
        const val TAG = "SearchDialogFragment"
    }

    interface OnSearchListener {
        fun onSearch(term: String)
    }
}