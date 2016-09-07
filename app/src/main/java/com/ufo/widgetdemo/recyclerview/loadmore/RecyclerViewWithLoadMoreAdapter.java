package com.ufo.widgetdemo.recyclerview.loadmore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufo.widgetdemo.DataModel;
import com.ufo.widgetdemo.R;
import com.ufo.widgetdemo.recyclerview.RecyclerViewSingleImageViewHolder;

import java.util.List;

/**
 * Created by tjpld on 16/9/7.
 */
public class RecyclerViewWithLoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;


    private List<DataModel> mData;
    private Context mContext;


    public RecyclerViewWithLoadMoreAdapter(List<DataModel> data, Context context) {
        mData = data;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_single_image_item, parent, false);
            return new RecyclerViewSingleImageViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecyclerViewSingleImageViewHolder) {
            DataModel dataModel = mData.get(position);
            RecyclerViewSingleImageViewHolder viewHolder = (RecyclerViewSingleImageViewHolder) holder;
            viewHolder.mTitle.setText(dataModel.getTitle());

        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.mProgressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


}
