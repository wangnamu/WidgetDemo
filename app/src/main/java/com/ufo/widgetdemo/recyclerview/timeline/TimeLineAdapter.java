package com.ufo.widgetdemo.recyclerview.timeline;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ufo.widgetdemo.R;

import java.util.List;

/**
 * Created by tjpld on 16/8/1.
 */
public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {

    private List<TimeLineModel> mDataSet;

    public TimeLineAdapter(List<TimeLineModel> models) {
        mDataSet = models;
    }

    @Override
    public int getItemViewType(int position) {
        final int size = mDataSet.size() - 1;
        if (size == 0)
            return ItemType.ATOM;
        else if (position == 0)
            return ItemType.START;
        else if (position == size)
            return ItemType.END;
        else return ItemType.NORMAL;
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_time_line_item, viewGroup, false);
        return new TimeLineViewHolder(v, viewType);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder timeLineViewHolder, int i) {
        timeLineViewHolder.setData(mDataSet.get(i));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }




}