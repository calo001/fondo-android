package com.github.calo001.fondo.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfiniteScrollListener(
    private val linearLayoutManager: LinearLayoutManager,
    private val listener: OnLoadMoreListener): RecyclerView.OnScrollListener() {
    var loading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (checkLoading() and checkItemCount() and checkIsNotOnTop(dy)) {
            listener.onLoadMore()
            loading = true
        }
    }

    private fun checkIsNotOnTop(dy: Int) = (dy > 0)
    private fun checkLoading() = !loading

    private fun checkItemCount(): Boolean {
        val totalItems = linearLayoutManager.itemCount
        val lastVisible= linearLayoutManager.findLastVisibleItemPosition()
        return totalItems <= lastVisible + VISIBLE_THRESHOLD
    }

    companion object {
        const val VISIBLE_THRESHOLD = 1
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }
}