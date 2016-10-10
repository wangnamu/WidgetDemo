package com.ufo.widgetdemo.recyclerview.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ufo.widgetdemo.R;

/**
 * Created by tjpld on 2016/9/26.
 */

public class HostViewHolder extends RecyclerView.ViewHolder {

    public View mFieldText;
    public View mFieldImage;
    public ImageView mHeadPortrait;
    public ImageView mImage;
    public TextView mContent;
    public TextView mTopTime;
    public TextView mItemMsg;
    public ProgressBar mContentProgressBar;
    public ProgressBar mImageProgressBar;

    public HostViewHolder(View itemView) {
        super(itemView);
        
        mFieldText = itemView.findViewById(R.id.recycler_view_chat_host_item_field_text);
        mFieldImage = itemView.findViewById(R.id.recycler_view_chat_host_item_field_image);

        mHeadPortrait = (ImageView) itemView.findViewById(R.id.recycler_view_chat_host_item_headPortrait);
        mImage = (ImageView) itemView.findViewById(R.id.recycler_view_chat_host_item_image);

        mContent = (TextView) itemView.findViewById(R.id.recycler_view_chat_host_item_text);
        mTopTime = (TextView) itemView.findViewById(R.id.recycler_view_chat_host_item_top_time);
        mItemMsg = (TextView) itemView.findViewById(R.id.recycler_view_chat_host_item_msg);

        mContentProgressBar = (ProgressBar) itemView.findViewById(R.id.recycler_view_chat_host_item_content_progressbar);
        mImageProgressBar = (ProgressBar) itemView.findViewById(R.id.recycler_view_chat_host_item_image_progressbar);


    }

    private void setContentWith(Context context) {

        int content_margin = context.getResources().getDimensionPixelOffset(R.dimen.chat_content_margin);
        int headPortrait_width = context.getResources().getDimensionPixelOffset(R.dimen.chat_headPortrait_width);
        int progress_width = context.getResources().getDimensionPixelOffset(R.dimen.chat_progress_width);
        int parent_padding = context.getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin) * 2;

        mContent.setMaxWidth(getScreenWidth(context) - content_margin - headPortrait_width - progress_width - parent_padding);
    }


    public void showFieldText(Context context) {
        mFieldText.setVisibility(View.VISIBLE);
        mFieldImage.setVisibility(View.GONE);

        setContentWith(context);
    }

    public void showFieldImage(Context context) {
        mFieldText.setVisibility(View.GONE);
        mFieldImage.setVisibility(View.VISIBLE);
    }

    public void showProgress() {
        if (mFieldText.getVisibility() == View.VISIBLE) {
            mContentProgressBar.setVisibility(View.VISIBLE);
        }
        else {
            mImageProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgress() {
        if (mFieldText.getVisibility() == View.VISIBLE) {
            mContentProgressBar.setVisibility(View.GONE);
        }
        else {
            mImageProgressBar.setVisibility(View.GONE);
        }
    }


    private int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        return width;
    }

}
