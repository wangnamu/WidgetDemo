package com.ufo.widgetdemo.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufo.widgetdemo.R;

/**
 * Created by tjpld on 16/9/6.
 */
public class RecyclerViewSingleImageViewHolder extends RecyclerView.ViewHolder{

    public TextView mTitle;
    public ImageView mImageView;

    public RecyclerViewSingleImageViewHolder(final View itemView) {
        super(itemView);
        mTitle = (TextView) itemView.findViewById(R.id.recycler_view_single_image_item_title);
        mImageView = (ImageView) itemView.findViewById(R.id.recycler_view_single_image_item_image);
    }

}
