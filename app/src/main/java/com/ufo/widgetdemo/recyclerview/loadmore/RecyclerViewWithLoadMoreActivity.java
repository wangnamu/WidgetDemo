package com.ufo.widgetdemo.recyclerview.loadmore;

import android.os.Handler;
import android.os.Message;
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
import java.util.Collections;
import java.util.List;

public class RecyclerViewWithLoadMoreActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerViewWithLoadMoreAdapter mAdapter;

    //线程安全的List
    private List<DataModel> mData = Collections.synchronizedList(new ArrayList<DataModel>());


    MyHandler mHandler = new MyHandler(this);

    RecyclerViewLoadMoreOnScrollListener scrollListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_with_load_more);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        addData(0, 20);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView_load_more);

        mAdapter = new RecyclerViewWithLoadMoreAdapter(mData, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mAdapter);

        scrollListener = new RecyclerViewLoadMoreOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                mData.add(null);
                mAdapter.notifyItemInserted(mData.size() - 1);

                Message message = new Message();
                message.what = 0;
                mHandler.sendMessageDelayed(message, 3000);
            }
        };


        mRecyclerView.addOnScrollListener(scrollListener);

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


    public void loadMore() {

        synchronized (mData) {

            mData.remove(mData.size() - 1);
            mAdapter.notifyItemRemoved(mData.size());

            int start = mData.size();
            int end = start + 20;

            addData(start, end);

            mAdapter.notifyDataSetChanged();
            scrollListener.setLoaded();
        }
    }


    public void addData(int start, int end) {
        for (int i = start; i < end; i++) {
            DataModel dataModel = new DataModel("Name " + i, null, null);
            mData.add(dataModel);
        }
    }


    private static class MyHandler extends Handler {

        private final WeakReference<RecyclerViewWithLoadMoreActivity> mActivity;

        public MyHandler(RecyclerViewWithLoadMoreActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            RecyclerViewWithLoadMoreActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 0:
                        activity.loadMore();
                        break;
                }
            }
        }
    }

}
