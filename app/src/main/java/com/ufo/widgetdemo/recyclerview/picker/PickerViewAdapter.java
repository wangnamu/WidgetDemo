package com.ufo.widgetdemo.recyclerview.picker;

import android.database.DataSetObserver;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by tjpld on 2016/10/27.
 */

public abstract class PickerViewAdapter<T> {

    private DataSetObserver mDataSetObserver;

    public void registerObserver(DataSetObserver observer) {
        mDataSetObserver = observer;
    }

    public void unregisterObserver() {
        mDataSetObserver = null;
    }

    public void notifyDataSetChanged() {
        if (mDataSetObserver != null) mDataSetObserver.onChanged();
    }

    public void notifyDataSetInvalidate() {
        if (mDataSetObserver != null) mDataSetObserver.onInvalidated();
    }

    public abstract PickerViewSearchAdapter<T> getPickerViewSearchAdapter();

    public abstract int getCount();

    public abstract T getItem(int position);

    public abstract View onMakeView(T t);

    public abstract void delItem(int position);

    public abstract void delLastItem();

    public abstract void dropDownOnItemClick(AdapterView<?> parent, View view, int position, long id);

    public abstract void onLayoutDropDown();
}
