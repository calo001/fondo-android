package io.codyffly.fondo.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfiniteScrollListener(
    private val linearLayoutManager: LinearLayoutManager,
    private val listener: OnLoadMoreListener): RecyclerView.OnScrollListener() {

    private val VISIBLE_THRESHOLD = 2
    var loading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (checkLoading() and checkItemCount() and checkIsNotOnTop(dx, dy)) {
            listener.onLoadMore()
            loading = true
        }
    }

    private fun checkIsNotOnTop(dx: Int, dy: Int) = (dy > 0)
    private fun checkLoading() = !loading

    private fun checkItemCount(): Boolean {
        val totalItems = linearLayoutManager.itemCount
        val lastVisible= linearLayoutManager.findLastVisibleItemPosition()
        return totalItems <= lastVisible + VISIBLE_THRESHOLD
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }
}