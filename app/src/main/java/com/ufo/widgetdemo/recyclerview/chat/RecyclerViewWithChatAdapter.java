package com.ufo.widgetdemo.recyclerview.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.ufo.widgetdemo.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by tjpld on 2016/9/26.
 */

public class RecyclerViewWithChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChatModel> mData;
    private Context mContext;

    public RecyclerViewWithChatAdapter(List<ChatModel> data, Context context) {
        mData = data;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ChatType.Host) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_chat_host_item, parent, false);
            HostViewHolder viewHolder = new HostViewHolder(view);
            return viewHolder;
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_chat_guest_item, parent, false);
            GuestViewHolder viewHolder = new GuestViewHolder(view);
            return viewHolder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HostViewHolder) {
            HostViewHolder viewHolder = (HostViewHolder) holder;

            if (position == 0) {
                viewHolder.mTopTime.setText(date2string(mData.get(position).getDate(), "E HH:mm"));
                viewHolder.mTopTime.setVisibility(View.VISIBLE);
            } else if (in15Min(mData.get(position - 1).getDate(), mData.get(position).getDate())) {
                viewHolder.mTopTime.setText(date2string(mData.get(position).getDate(), "E HH:mm"));
                viewHolder.mTopTime.setVisibility(View.VISIBLE);
            } else {
                viewHolder.mTopTime.setVisibility(View.GONE);
            }

            if (mData.get(position).getDataType() == DataType.Text) {

                viewHolder.showFieldText(mContext);

                viewHolder.mContent.setText(mData.get(position).getContent());

                Glide.with(mContext)
                        .load(mData.get(position).getHeadPortrait())
                        .placeholder(R.drawable.ic_placeholder_round)
                        .bitmapTransform(new CenterCrop(mContext), new CropCircleTransformation(mContext))
                        .dontAnimate()
                        .into(viewHolder.mHeadPortrait);

                int sendStatus = mData.get(position).getSendStatusType();

                if (sendStatus == SendStatusType.Sending) {
                    viewHolder.showProgress();
                } else if (sendStatus == SendStatusType.Sended) {
                    viewHolder.hideProgress();
                }


            } else {

                viewHolder.showFieldImage(mContext);

                Glide.with(mContext)
                        .load(mData.get(position).getHeadPortrait())
                        .placeholder(R.drawable.ic_placeholder_round)
                        .bitmapTransform(new CenterCrop(mContext), new CropCircleTransformation(mContext))
                        .dontAnimate()
                        .into(viewHolder.mHeadPortrait);

                Glide.with(mContext)
                        .load(mData.get(position).getContent())
                        .thumbnail(0.1f)
                        .placeholder(R.drawable.ic_placeholder)
                        .override(500, 500)
                        .bitmapTransform(new FitCenter(mContext),
                                new RoundedCornersTransformation(mContext, 60, 0))
                        .dontAnimate()
                        .into(viewHolder.mImage);

                int sendStatus = mData.get(position).getSendStatusType();

                if (sendStatus == SendStatusType.Sending) {
                    viewHolder.showProgress();
                } else if (sendStatus == SendStatusType.Sended) {
                    viewHolder.hideProgress();
                }

            }
        } else if (holder instanceof GuestViewHolder) {

            GuestViewHolder viewHolder = (GuestViewHolder) holder;

            if (position == 0) {
                viewHolder.mTopTime.setText(date2string(mData.get(position).getDate(), "E HH:mm "));
                viewHolder.mTopTime.setVisibility(View.VISIBLE);
            } else if (in15Min(mData.get(position - 1).getDate(), mData.get(position).getDate())) {
                viewHolder.mTopTime.setText(date2string(mData.get(position).getDate(), "E HH:mm"));
                viewHolder.mTopTime.setVisibility(View.VISIBLE);
            } else {
                viewHolder.mTopTime.setVisibility(View.GONE);
            }


            if (mData.get(position).getDataType() == DataType.Text) {

                viewHolder.showFieldText(mContext);

                viewHolder.mContent.setText(mData.get(position).getContent());

                Glide.with(mContext)
                        .load(mData.get(position).getHeadPortrait())
                        .placeholder(R.drawable.ic_placeholder_round)
                        .bitmapTransform(new CenterCrop(mContext), new CropCircleTransformation(mContext))
                        .dontAnimate()
                        .into(viewHolder.mHeadPortrait);
            } else {

                viewHolder.showFieldImage(mContext);

                Glide.with(mContext)
                        .load(mData.get(position).getHeadPortrait())
                        .placeholder(R.drawable.ic_placeholder_round)
                        .bitmapTransform(new CenterCrop(mContext), new CropCircleTransformation(mContext))
                        .dontAnimate()
                        .into(viewHolder.mHeadPortrait);

                Glide.with(mContext)
                        .load(mData.get(position).getContent())
                        .thumbnail(0.1f)
                        .placeholder(R.drawable.ic_placeholder)
                        .override(500, 500)
                        .bitmapTransform(new FitCenter(mContext),
                                new RoundedCornersTransformation(mContext, 60, 0))
                        .dontAnimate()
                        .into(viewHolder.mImage);
            }

        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getChatType();
    }


    public static String date2string(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static boolean in15Min(Date startDate, Date endDate) {
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;


        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        if (elapsedDays > 0) {
            return true;
        }

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        if (elapsedHours > 0) {
            return true;
        }

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        if (elapsedMinutes >= 15) {
            return true;
        }

        return false;
    }

    public static String diff(Date startDate, Date endDate) {

        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;


        if (elapsedDays > 0) {
            return elapsedDays + "天";
        }
        if (elapsedHours > 0) {
            return elapsedHours + "小时";
        }
        if (elapsedMinutes > 0) {
            return elapsedMinutes + "分钟";
        }

        return elapsedSeconds + "秒";
    }


}
