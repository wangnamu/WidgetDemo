package com.ufo.widgetdemo.recyclerview.refresh;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.ufo.widgetdemo.DataModel;
import com.ufo.widgetdemo.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewWithRefreshActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private RecyclerViewWithRefreshAdapter mAdapter;
    private List<DataModel> mData = new ArrayList<>();
    private SwipeRefreshLayout mSwipeLayout;

    private MyHandler mHandler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_with_refresh);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        initData();

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        if (mSwipeLayout != null) {
            mSwipeLayout.setOnRefreshListener(this);
            mSwipeLayout.setColorSchemeResources(R.color.colorAccent);
            mSwipeLayout.setDistanceToTriggerSync(400);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_refresh);

        mAdapter = new RecyclerViewWithRefreshAdapter(mData, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void initData() {
        for (int i = 0; i < 40; i++) {
            DataModel dataModel = new DataModel("Title——Item" + i, null, null);
            mData.add(dataModel);
        }
    }


    private void refresh() {

        mSwipeLayout.setRefreshing(false);
        mData.clear();
        initData();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {

        Message msg = new Message();
        msg.what = 0;
        mHandler.sendMessageDelayed(msg, 3000);

    }


    private static class MyHandler extends Handler {

        private final WeakReference<RecyclerViewWithRefreshActivity> mActivity;

        public MyHandler(RecyclerViewWithRefreshActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            RecyclerViewWithRefreshActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 0:
                        activity.refresh();
                        break;
                }
            }
        }
    }


}
