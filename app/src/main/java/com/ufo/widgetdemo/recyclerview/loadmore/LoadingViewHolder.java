package com.ufo.widgetdemo.recyclerview.loadmore;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.ufo.widgetdemo.R;

/**
 * Created by tjpld on 16/9/7.
 */
public class LoadingViewHolder extends RecyclerView.ViewHolder {
    public ProgressBar mProgressBar;

    public LoadingViewHolder(View itemView) {
        super(itemView);
        mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
    }
}
