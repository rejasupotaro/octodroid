package com.example.octodroid.views.components

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

abstract class LinearLayoutLoadMoreListener(private val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    private var prevTotalCount = 0

    private var isLoading = true

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = recyclerView!!.getChildCount()
        val totalItemCount = layoutManager.getItemCount()
        val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

        if (isLoading) {
            if (prevTotalCount < totalItemCount) {
                isLoading = false
                prevTotalCount = totalItemCount
            }
        } else {
            if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                onLoadMore()
                isLoading = true
            }
        }
    }

    abstract fun onLoadMore()
}

