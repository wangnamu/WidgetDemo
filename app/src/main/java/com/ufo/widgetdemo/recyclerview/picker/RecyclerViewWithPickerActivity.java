package com.ufo.widgetdemo.recyclerview.picker;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ufo.widgetdemo.DataModel;
import com.ufo.widgetdemo.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewWithPickerActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RecyclerViewWithPickerAdapter mAdapter;

    private List<DataModel> mData = new ArrayList<>();
    private List<DataModel> mSelected = new ArrayList<>();

    private PickerView mPickerView;
    private PickerViewAdapter<DataModel> mPickerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_with_picker);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initData();


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_picker);

        mAdapter = new RecyclerViewWithPickerAdapter(mData, this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mAdapter);


        mPickerView = (PickerView) findViewById(R.id.picker_view);
        mPickerViewAdapter = new PickerViewAdapter<DataModel>() {

            @Override
            public PickerViewSearchAdapter<DataModel> getPickerViewSearchAdapter() {
                return new PickerViewSearchAdapter<>(RecyclerViewWithPickerActivity.this, mData, "title");
            }

            @Override
            public int getCount() {
                return mSelected.size();
            }

            @Override
            public DataModel getItem(int position) {
                return mSelected.get(position);
            }

            @Override
            public View onMakeView(DataModel dataModel) {

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final TextView textView = (TextView) inflater.inflate(R.layout.view_token, null);
                textView.setText(dataModel.getTitle());

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.setMargins(0, 0, 8, 0);

                textView.setLayoutParams(params);

                return textView;
            }

            @Override
            public void delItem(int position) {
                deleteItem(position);
            }

            @Override
            public void delLastItem() {
                int c = mSelected.size() - 1;
                if (c >= 0) {
                    deleteItem(c);
                }
            }

            @Override
            public void dropDownOnItemClick(AdapterView<?> parent, View view, int position, long id) {
                addItem(position);
            }

            @Override
            public void onLayoutDropDown() {
                mPickerView.getmAutoCompleteTextView().setDropDownAnchor(R.id.picker_view);
                mPickerView.getmAutoCompleteTextView().setDropDownHeight(mRecyclerView.getHeight());
                mPickerView.getmAutoCompleteTextView().setDropDownVerticalOffset(mPickerView.getPaddingBottom());
            }


        };

        mPickerView.setAdapter(mPickerViewAdapter);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void initData() {

        for (int i = 0; i < 40; i++) {
            DataModel dataModel = new DataModel("Item" + i, null, null);
            mData.add(dataModel);
        }

    }


    private void addItem(int position) {
        DataModel dataModel = mData.get(position);
        if (!mSelected.contains(dataModel)) {
            mSelected.add(dataModel);
            mAdapter.notifyDataSetChanged();
            mPickerViewAdapter.notifyDataSetChanged();
        }
    }

    private void deleteItem(int position) {
        mSelected.remove(position);
        mPickerViewAdapter.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
    }


    public class RecyclerViewWithPickerAdapter extends RecyclerView.Adapter<RecyclerViewWithPickerViewHolder> {

        private List<DataModel> mData;
        private Context mContext;


        public RecyclerViewWithPickerAdapter(List<DataModel> data, Context context) {
            this.mContext = context;
            this.mData = data;
        }


        @Override
        public RecyclerViewWithPickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.expand_checked_child_item, parent, false);
            RecyclerViewWithPickerViewHolder viewHolder = new RecyclerViewWithPickerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerViewWithPickerViewHolder holder, final int position) {
            holder.mTitle.setText(mData.get(position).getTitle());

            final boolean isChecked = mSelected.contains(mData.get(position));
            if (!isChecked) {
                holder.mCheckBox.setChecked(false);
            } else {
                holder.mCheckBox.setChecked(true);
            }


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isChecked) {
                        addItem(position);
                    } else {
                        int pos = mSelected.indexOf(mData.get(position));
                        deleteItem(pos);
                    }
                }
            });

        }


        @Override
        public int getItemCount() {
            return mData.size();
        }
    }


    static class RecyclerViewWithPickerViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitle;
        public CheckBox mCheckBox;

        public RecyclerViewWithPickerViewHolder(final View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.expand_checked_child_item_text);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.expand_checked_child_item_checkbox);
        }

    }


}
