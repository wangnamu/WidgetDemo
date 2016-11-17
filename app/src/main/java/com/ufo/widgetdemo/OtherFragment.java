package com.ufo.widgetdemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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


public class OtherFragment extends Fragment {

    private List<DataModel> mData;

    private RecyclerView mRecyclerView;
    private MainRecyclerViewAdapter mAdapter;

    private OnFragmentInteractionListener mListener;

    public OtherFragment() {
        mData = new ArrayList<>();
        mData.add(new DataModel("none", "遮罩", ""));
        mData.add(new DataModel("none", "ExpandTabView", ""));
        mData.add(new DataModel("none", "等待条dialog", ""));
        mData.add(new DataModel("none", "选择时间dialog", ""));
        mData.add(new DataModel("none", "搜索", ""));
        mData.add(new DataModel("none", "选择联系人", ""));
        mData.add(new DataModel("none", "表单", ""));

    }


    // TODO: Rename and change types and number of parameters
    public static OtherFragment newInstance() {
        OtherFragment fragment = new OtherFragment();
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
        View view = inflater.inflate(R.layout.fragment_other, container, false);

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



    //Fragment显示隐藏
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mListener != null) {
            mListener.onFragmentInteraction("OtherViewFragment");
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
