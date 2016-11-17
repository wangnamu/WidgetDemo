package com.ufo.widgetdemo.recyclerview.chat;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.ufo.widgetdemo.R;
import com.ufo.widgetdemo.Utils;

import java.util.Date;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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

    public void setTopTime(int position, Date current, Date last) {
        if (position == 0) {
            mTopTime.setText(Utils.date2string(current));
            mTopTime.setVisibility(View.VISIBLE);
        } else if (Utils.in15Min(last, current)) {
            mTopTime.setText(Utils.date2string(current));
            mTopTime.setVisibility(View.VISIBLE);
        } else {
            mTopTime.setVisibility(View.GONE);
        }
    }

    public void setHolder(Context context, ChatModel chatModel) {

        if (chatModel.getDataType() == DataType.Text) {

            showFieldText(context);

            mContent.setText(chatModel.getContent());

            Glide.with(context)
                    .load(chatModel.getHeadPortrait())
                    .placeholder(R.drawable.ic_placeholder_round)
                    .bitmapTransform(new CenterCrop(context), new CropCircleTransformation(context))
                    .dontAnimate()
                    .into(mHeadPortrait);


        } else {

            showFieldImage(context);

            Glide.with(context)
                    .load(chatModel.getHeadPortrait())
                    .placeholder(R.drawable.ic_placeholder_round)
                    .bitmapTransform(new CenterCrop(context), new CropCircleTransformation(context))
                    .dontAnimate()
                    .into(mHeadPortrait);

            Glide.with(context)
                    .load(chatModel.getContent())
                    .thumbnail(0.1f)
                    .placeholder(R.drawable.ic_placeholder)
                    .override(500, 500)
                    .bitmapTransform(new FitCenter(context),
                            new RoundedCornersTransformation(context, context.getResources().getDimensionPixelOffset(R.dimen.chat_item_radius), 0))
                    .dontAnimate()
                    .into(mImage);


        }

        refresh(chatModel);
    }


    private void setContentWith(Context context) {

        int content_margin = context.getResources().getDimensionPixelOffset(R.dimen.chat_content_margin);
        int headPortrait_width = context.getResources().getDimensionPixelOffset(R.dimen.chat_headPortrait_width);
        int progress_width = context.getResources().getDimensionPixelOffset(R.dimen.chat_progress_width);
        int parent_padding = context.getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin) * 2;

        mContent.setMaxWidth(Utils.getScreenWidth(context) - content_margin - headPortrait_width - progress_width - parent_padding);
    }


    private void showFieldText(Context context) {
        mFieldText.setVisibility(View.VISIBLE);
        mFieldImage.setVisibility(View.GONE);

        setContentWith(context);
    }

    private void showFieldImage(Context context) {
        mFieldText.setVisibility(View.GONE);
        mFieldImage.setVisibility(View.VISIBLE);
    }


    private void showProgress() {
        if (mFieldText.getVisibility() == View.VISIBLE) {
            mContentProgressBar.setVisibility(View.VISIBLE);
        } else {
            mImageProgressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgress() {
        if (mFieldText.getVisibility() == View.VISIBLE) {
            mContentProgressBar.setVisibility(View.GONE);
        } else {
            mImageProgressBar.setVisibility(View.GONE);
        }
    }

    private void showErrorMsg(ChatModel chatModel) {
        if (mItemMsg.getVisibility() != View.VISIBLE) {
            mItemMsg.setVisibility(View.VISIBLE);
            mItemMsg.setText(String.format("%s 发送失败", Utils.date2string(chatModel.getDate())));
            mItemMsg.setTextColor(Color.RED);
        }
    }

    private void hideErrorMsg() {
        if (mItemMsg.getVisibility() != View.GONE) {
            mItemMsg.setVisibility(View.GONE);
        }
    }


    public void refresh(ChatModel chatModel) {
        int sendStatus = chatModel.getSendStatusType();
        if (sendStatus == SendStatusType.Sending) {
            showProgress();
            hideErrorMsg();
        } else if (sendStatus == SendStatusType.Sended) {
            hideProgress();
            hideErrorMsg();
        } else if (sendStatus == SendStatusType.Error) {
            hideProgress();
            showErrorMsg(chatModel);
        }
    }

}
