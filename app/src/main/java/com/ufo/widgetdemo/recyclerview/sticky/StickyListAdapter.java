package com.ufo.widgetdemo.recyclerview.sticky;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.turingtechnologies.materialscrollbar.INameableAdapter;
import com.ufo.widgetdemo.DataModel;
import com.ufo.widgetdemo.R;

import java.util.List;

/**
 * Created by tjpld on 16/9/22.
 */

public class StickyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> , INameableAdapter {

    private List<DataModel> mData;
    private Context mContext;

    public StickyListAdapter(List<DataModel> data, Context context) {
        mData = data;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_sticky_content_item, parent, false);
        return new StickyContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DataModel dataModel = mData.get(position);
        StickyContentViewHolder viewHolder = (StickyContentViewHolder) holder;
        viewHolder.mTitle.setText(dataModel.getTitle());
    }

    @Override
    public long getHeaderId(int position) {
        return mData.get(position).getTitle().charAt(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_sticky_header_item, parent, false);
        return new StickyHeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        DataModel dataModel = mData.get(position);
        StickyHeaderViewHolder viewHolder = (StickyHeaderViewHolder) holder;
        viewHolder.mTitle.setText(String.valueOf(dataModel.getTitle().charAt(0)));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    //表头在内容视图的左边
    @Override
    public boolean isHeaderInLeft() {
        return true;
    }


    //根据首字母快速索引
    @Override
    public Character getCharacterForElement(int element) {
        Character c = mData.get(element).getTitle().charAt(0);
        if(Character.isDigit(c)){
            c = '#';
        }
        return c;
    }
}
