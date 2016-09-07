package com.ufo.widgetdemo.recyclerview.timeline;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ufo.widgetdemo.R;


/**
 * Created by tjpld on 16/8/1.
 */
public class TimeLineViewHolder extends RecyclerView.ViewHolder {

    private TextView mName;

    public TimeLineViewHolder(View itemView, int type) {
        super(itemView);

        mName = (TextView) itemView.findViewById(R.id.recycler_view_time_line_item_text);

        TimeLineMarker mMarker = (TimeLineMarker) itemView.findViewById(R.id.recycler_view_time_line_item_mark);
        if (type == ItemType.ATOM) {
            mMarker.setBeginLine(null);
            mMarker.setEndLine(null);
        } else if (type == ItemType.START) {
            mMarker.setBeginLine(null);
        } else if (type == ItemType.END) {
            mMarker.setEndLine(null);
        }

    }

    public void setData(TimeLineModel data) {
        mName.setText("Name:" + data.getName() + " Age:" + data.getAge());
    }
}


