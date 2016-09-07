package com.ufo.widgetdemo.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ufo.widgetdemo.R;


/**
 * Created by tjpld on 16/9/5.
 */
public class RecyclerViewSingleViewHolder extends RecyclerView.ViewHolder {

    public TextView mTitle;

    public RecyclerViewSingleViewHolder(final View itemView) {
        super(itemView);
        mTitle = (TextView) itemView.findViewById(R.id.recycler_view_single_item_title);
    }

}