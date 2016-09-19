package com.ufo.widgetdemo.animation.fabwithtoolbar;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.bowyer.app.fabtoolbar.FabToolbar;
import com.ufo.widgetdemo.DataModel;
import com.ufo.widgetdemo.R;
import com.ufo.widgetdemo.common.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class FabWithToolbarActivity extends AppCompatActivity {


    private List<DataModel> mData;

    private ActionBar mActionBar;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;

    private FloatingActionButton mFab;
    private FabToolbar mFabToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab_with_toolbar);

        initData();
        initActionBar();

        initControl();
        initFabToolbar();

    }


    private void initActionBar() {
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void initData() {
        mData = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            DataModel dataModel = new DataModel("Title" + i, "SubHead" + i, null);
            mData.add(dataModel);
        }
    }


    private void initFabToolbar() {

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFabToolbar = (FabToolbar) findViewById(R.id.fabtoolbar);

        mRecyclerView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                mFabToolbar.collapse();
            }
        });

        mFabToolbar.setFab(mFab);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFabToolbar.expandFab();
            }
        });

    }


    private void initControl() {

        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);

        mAdapter = new RecyclerViewAdapter(mData, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onBackPressed() {
        if (mFabToolbar.isFabExpanded()) {
            mFabToolbar.collapse();
            return;
        }
        finish();
        super.onBackPressed();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    static class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewViewHolder> {

        private List<DataModel> mData;
        private Context mContext;


        public RecyclerViewAdapter(List<DataModel> data, Context context) {
            this.mContext = context;
            this.mData = data;
        }


        @Override
        public RecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item, parent, false);
            RecyclerViewViewHolder viewHolder = new RecyclerViewViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerViewViewHolder holder, int position) {
            holder.mTitle.setText(mData.get(position).getTitle());
            holder.mSubHead.setText(mData.get(position).getSubhead());
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }


    static class RecyclerViewViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle;
        TextView mSubHead;

        public RecyclerViewViewHolder(final View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.recycler_view_item_title);
            mSubHead = (TextView) itemView.findViewById(R.id.recycler_view_item_subhead);
        }

    }


}
