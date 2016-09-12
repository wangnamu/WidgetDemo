package com.ufo.widgetdemo.animation.fabwithtoolbar;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.konifar.fab_transformation.FabTransformation;
import com.ufo.widgetdemo.DataModel;
import com.ufo.widgetdemo.R;
import com.ufo.widgetdemo.common.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class FabWithToolbarActivity extends AppCompatActivity {


    private List<DataModel> mData;

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;

    private FloatingActionButton mFab;
    private View mToolbarFooter;

    private boolean isTransforming;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab_with_toolbar);

        mData = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            DataModel dataModel = new DataModel("Title" + i, "SubHead" + i, null);
            mData.add(dataModel);
        }


        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mToolbarFooter = findViewById(R.id.toolbar_footer);

        mAdapter = new RecyclerViewAdapter(mData, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (mFab.getVisibility() != View.VISIBLE && !isTransforming) {
                    FabTransformation.with(mFab)
                            .setListener(new FabTransformation.OnTransformListener() {
                                @Override
                                public void onStartTransform() {
                                    isTransforming = true;
                                }

                                @Override
                                public void onEndTransform() {
                                    isTransforming = false;
                                }
                            })
                            .transformFrom(mToolbarFooter);
                }
            }
        });


        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFab.getVisibility() == View.VISIBLE) {
                    FabTransformation.with(mFab).transformTo(mToolbarFooter);
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (mFab.getVisibility() != View.VISIBLE) {
            FabTransformation.with(mFab).transformFrom(mToolbarFooter);
            return;
        }
        super.onBackPressed();
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
