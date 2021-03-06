package com.ufo.widgetdemo.recyclerview.sticky;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ufo.widgetdemo.R;

/**
 * Created by tjpld on 16/9/21.
 */

public class StickyContentViewHolder extends RecyclerView.ViewHolder{

    TextView mTitle;

    public StickyContentViewHolder(View itemView) {
        super(itemView);
        mTitle = (TextView)itemView.findViewById(R.id.recycler_view_sticky_content_title);
    }
}
