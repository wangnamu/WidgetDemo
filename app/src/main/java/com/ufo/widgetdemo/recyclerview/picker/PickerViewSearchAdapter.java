package com.ufo.widgetdemo.recyclerview.picker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.ufo.widgetdemo.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tjpld on 2016/11/16.
 */

public class PickerViewSearchAdapter<T> extends BaseAdapter implements Filterable {

    private Context mContext;
    private List<T> mData;
    private List<T> mQuery;
    private SearchFilter mSearchFilter;
    private String mSearchField;

    public PickerViewSearchAdapter(Context context, List<T> data, String searchField) {
        mContext = context;
        mData = data;
        mSearchField = searchField;
        mQuery = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mQuery.size();
    }

    @Override
    public Object getItem(int position) {
        return mQuery.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        T t = mQuery.get(position);

        View view = inflater.inflate(R.layout.recycler_view_single_item, parent, false);

        TextView textView = (TextView) view.findViewById(R.id.recycler_view_single_item_title);

        String str = getSearchField(t);
        if (str != null) {
            textView.setText(str);
        }

        return view;
    }


    @Override
    public Filter getFilter() {
        if (mSearchFilter == null) {
            mSearchFilter = new SearchFilter();
        }
        return mSearchFilter;
    }


    public int getDataSourcePositionInQuery(int position) {
        T t = mQuery.get(position);
        return mData.indexOf(t);
    }


    private String getSearchField(T t) {
        try {

            Field field = t.getClass().getDeclaredField(mSearchField);
            field.setAccessible(true);
            Object obj = field.get(t);
            String str = obj.toString();

            return str;

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }


    private class SearchFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<T> queryResult = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                results.values = queryResult;
                results.count = queryResult.size();
                return results;
            }

            for (T t : mData) {

                String str = getSearchField(t);
                if (str != null) {
                    if (str.contains(constraint.toString())) {
                        queryResult.add(t);
                    }
                }
            }

            results.values = queryResult;
            results.count = queryResult.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<T> queryResult = (List<T>) results.values;

            if (mQuery.size() > 0) {
                mQuery.clear();
            }

            if (queryResult != null && queryResult.size() > 0) {
                for (T t : queryResult) {
                    mQuery.add(t);
                    notifyDataSetChanged();
                }
            }
        }

    }
}