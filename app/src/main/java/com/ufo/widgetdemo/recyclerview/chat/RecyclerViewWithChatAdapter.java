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

            viewHolder.setTopTime(position, mData.get(position).getDate(), position - 1 >= 0 ? mData.get(position - 1).getDate() : null);
            viewHolder.setHolder(mContext, mData.get(position));
        } else if (holder instanceof GuestViewHolder) {
            GuestViewHolder viewHolder = (GuestViewHolder) holder;

            viewHolder.setTopTime(position, mData.get(position).getDate(), position - 1 >= 0 ? mData.get(position - 1).getDate() : null)
            ;
            viewHolder.setHolder(mContext, mData.get(position));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            if (holder instanceof HostViewHolder) {
                HostViewHolder viewHolder = (HostViewHolder) holder;
                viewHolder.refresh((ChatModel) payloads.get(0));
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


}
