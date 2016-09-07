package com.ufo.widgetdemo.recyclerview.refresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufo.widgetdemo.DataModel;
import com.ufo.widgetdemo.R;
import com.ufo.widgetdemo.recyclerview.RecyclerViewSingleViewHolder;

import java.util.List;

/**
 * Created by tjpld on 16/9/7.
 */
public class RecyclerViewWithRefreshAdapter extends RecyclerView.Adapter<RecyclerViewSingleViewHolder> {

    private List<DataModel> mData;
    private Context mContext;


    public RecyclerViewWithRefreshAdapter(List<DataModel> data, Context context) {
        this.mContext = context;
        this.mData = data;
    }


    @Override
    public RecyclerViewSingleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_single_item, parent, false);
        RecyclerViewSingleViewHolder viewHolder = new RecyclerViewSingleViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewSingleViewHolder holder, int position) {
        holder.mTitle.setText(mData.get(position).getTitle());
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }
}
