package com.ufo.widgetdemo.recyclerview.loadmore;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by tjpld on 16/9/7.
 */
public abstract class RecyclerViewLoadMoreOnScrollListener extends RecyclerView.OnScrollListener {

    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, lastCompletelyVisibleItem, totalItemCount;

    private LinearLayoutManager mLinearLayoutManager;

    public RecyclerViewLoadMoreOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        totalItemCount = mLinearLayoutManager.getItemCount();
        lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
        lastCompletelyVisibleItem = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();


        if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)
                && lastCompletelyVisibleItem < lastVisibleItem) {
            onLoadMore();
            isLoading = true;
        }

    }

    public void setLoaded() {
        isLoading = false;
    }

    public abstract void onLoadMore();
}
