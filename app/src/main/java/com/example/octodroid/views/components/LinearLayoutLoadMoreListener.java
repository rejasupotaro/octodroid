package com.example.octodroid.views.components;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class LinearLayoutLoadMoreListener extends RecyclerView.OnScrollListener {

    private int prevTotalCount = 0;

    private boolean isLoading = true;

    private LinearLayoutManager layoutManager;

    public LinearLayoutLoadMoreListener(LinearLayoutManager linearLayoutManager) {
        this.layoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

        if (isLoading) {
            if (prevTotalCount < totalItemCount) {
                isLoading = false;
                prevTotalCount = totalItemCount;
            }
        } else {
            if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                onLoadMore();
                isLoading = true;
            }
        }
    }

    public abstract void onLoadMore();
}

