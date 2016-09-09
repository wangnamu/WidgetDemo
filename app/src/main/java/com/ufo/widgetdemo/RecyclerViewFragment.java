package com.ufo.widgetdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ufo.widgetdemo.common.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewFragment extends Fragment {

    private List<DataModel> mData;

    private RecyclerView mRecyclerView;
    private MainRecyclerViewAdapter mAdapter;

    public RecyclerViewFragment() {

        mData = new ArrayList<>();
        mData.add(new DataModel("RecyclerViewWithRefresh", "下拉刷新", "com.ufo.widgetdemo.recyclerview.refresh.RecyclerViewWithRefreshActivity"));
        mData.add(new DataModel("RecyclerViewWithLoadMore", "上拉更多", "com.ufo.widgetdemo.recyclerview.loadmore.RecyclerViewWithLoadMoreActivity"));
        mData.add(new DataModel("RecyclerViewWithTimeLine","时间轴","com.ufo.widgetdemo.recyclerview.timeline.RecyclerViewWithTimeLineActivity"));
        mData.add(new DataModel("RecyclerViewWithChecked", "支持多选", "com.ufo.widgetdemo.a"));
        mData.add(new DataModel("RecyclerViewWithGroup", "带分组", "com.ufo.widgetdemo.a"));
        mData.add(new DataModel("RecyclerViewWithCardView", "卡片式", "com.ufo.widgetdemo.a"));
        mData.add(new DataModel("ExpandableListView", "支持展开", "com.ufo.widgetdemo.a"));
        mData.add(new DataModel("ExpandableCheckedListView", "支持展开并且多选", "com.ufo.widgetdemo.recyclerview.expandchecked.ExpandCheckedActivity"));
        mData.add(new DataModel("RecyclerViewWithChat", "对话式", "com.ufo.widgetdemo.a"));

    }


    public static RecyclerViewFragment newInstance() {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.main_recycler_view);

        mAdapter = new MainRecyclerViewAdapter(mData, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        mAdapter.setRecyclerViewItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                DataModel dataModel = mData.get(position);

                try {
                    Intent intent = new Intent(getContext(), Class.forName(dataModel.getActivtyName()));
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }





    static class MainRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewViewHolder> {

        private List<DataModel> mData;
        private Context mContext;
        private RecyclerViewItemClickListener mRecyclerViewItemClickListener;


        public void setRecyclerViewItemClickListener(RecyclerViewItemClickListener recyclerViewItemClickListener) {
            this.mRecyclerViewItemClickListener = recyclerViewItemClickListener;
        }

        public MainRecyclerViewAdapter(List<DataModel> data, Context context) {
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
        public void onBindViewHolder(RecyclerViewViewHolder holder, final int position) {
            holder.mTitle.setText(mData.get(position).getTitle());
            holder.mSubHead.setText(mData.get(position).getSubhead());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRecyclerViewItemClickListener != null) {
                        mRecyclerViewItemClickListener.onItemClick(v, position);
                    }
                }
            });
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

    public interface RecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

}
