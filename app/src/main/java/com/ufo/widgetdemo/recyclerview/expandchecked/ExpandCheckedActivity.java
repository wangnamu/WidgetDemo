package com.ufo.widgetdemo.recyclerview.expandchecked;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufo.widgetdemo.R;

import java.util.ArrayList;
import java.util.List;

public class ExpandCheckedActivity extends AppCompatActivity {

    public static final String EXTRA_IN_EXPANDCHECK = "EXTRA_EXPANDCHECK";
    public static final String EXTRA_OUT_EXPANDCHECK = "EXTRA_OUT_EXPANDCHECK";


    private ExpandableListView mExpandableListView;
    private MyAdapter mAdapter;

    private ArrayList<IParent> mData;

    private ActionBar mActionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_checked);

        initActionBar();
        initData();
        initControl();
    }

    protected void initActionBar() {
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    protected void initData() {

        mData = getIntent().getParcelableArrayListExtra(EXTRA_IN_EXPANDCHECK);


        /**
         * 测试数据
         */
        if (mData == null) {

            mData = new ArrayList<>();

            for (int i = 0; i < 20; i++) {

                ParentModel parentModel = new ParentModel();
                parentModel.setChecked(false);
                parentModel.setDisplayName("Item" + i);

                List<ChildModel> selectableChildrenModelList = new ArrayList<>();

                for (int j = 0; j < 4; j++) {
                    ChildModel childrenModel = new ChildModel();
                    childrenModel.setChecked(false);
                    childrenModel.setDisplayName("ChildItem" + j);
                    selectableChildrenModelList.add(childrenModel);
                }

                parentModel.setChildrenList(selectableChildrenModelList);

                mData.add(parentModel);
            }

        }

    }


    protected void initControl() {

        mExpandableListView = (ExpandableListView) findViewById(R.id.expandable_checked_list_view);

        mAdapter = new MyAdapter();

        mExpandableListView.setAdapter(mAdapter);

        mExpandableListView.setGroupIndicator(null);


        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                IChild child = mData.get(groupPosition).getChildrenList().get(childPosition);

                if (!child.isChecked()) {
                    child.setChecked(true);
                } else {
                    child.setChecked(false);
                }



                boolean checked = mData.get(groupPosition).isChildrenCheckedAll();
                mData.get(groupPosition).setChecked(checked);


                mAdapter.notifyDataSetChanged();
                return false;

            }
        });


        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /**
         * 为demo效果，禁用下面代码
         */
        /*
        getMenuInflater().inflate(R.menu.menu_expand_checked, menu);
        */
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        /**
         * 为demo效果，禁用下面代码
         */
        /*
        if (item.getItemId() == R.id.menu_expand_checked_item_ok) {
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra(EXTRA_OUT_EXPANDCHECK, mData);
            setResult(RESULT_OK, intent);
            finish();
        }
        */
        return super.onOptionsItemSelected(item);
    }


    protected class MyAdapter extends BaseExpandableListAdapter {
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return mData.get(groupPosition).getChildrenList().get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(ExpandCheckedActivity.this, R.layout.expand_checked_child_item,
                        null);
                holder.mChildText = (TextView) convertView
                        .findViewById(R.id.expand_checked_child_item_text);
                holder.mChildCheckBox = (CheckBox) convertView
                        .findViewById(R.id.expand_checked_child_item_checkbox);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.mChildText.setText(mData.get(groupPosition).getChildrenList()
                    .get(childPosition).getDisplayName());
            boolean isChecked = mData.get(groupPosition).getChildrenList().get(childPosition).isChecked();
            if (!isChecked) {
                holder.mChildCheckBox.setChecked(false);
            } else {
                holder.mChildCheckBox.setChecked(true);
            }
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            // TODO Auto-generated method stub
            return mData.get(groupPosition).getChildrenList().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return mData.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            // TODO Auto-generated method stub
            return mData.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            // TODO Auto-generated method stub
            return groupPosition;
        }

        @Override
        public View getGroupView(final int groupPosition,
                                 final boolean isExpanded, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(ExpandCheckedActivity.this, R.layout.expand_checked_parent_item, null);
                holder.mParentText = (TextView) convertView
                        .findViewById(R.id.expand_checked_parent_item_text);
                holder.mParentCheckBox = (CheckBox) convertView
                        .findViewById(R.id.expand_checked_parent_item_checkbox);
                holder.mExpandImage = (ImageView) convertView.findViewById(R.id.expand_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mParentText.setText(mData.get(groupPosition).getDisplayName());

            final boolean isGroupCheckd = mData.get(groupPosition).isChecked();

            if (!isGroupCheckd) {
                holder.mParentCheckBox.setChecked(false);
            } else {
                holder.mParentCheckBox.setChecked(true);
            }


            if (isExpanded) {
                holder.mExpandImage.setBackgroundResource(R.drawable.ic_expand_less_24dp);
            } else {
                holder.mExpandImage.setBackgroundResource(R.drawable.ic_expand_more_24dp);
            }




            holder.mParentCheckBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    CheckBox groupBox = (CheckBox) v
                            .findViewById(R.id.expand_checked_parent_item_checkbox);

                    if (!isGroupCheckd) {
                        groupBox.setChecked(true);
                        mData.get(groupPosition).setChecked(true);

                        List<IChild> list = (List<IChild>) mData.get(groupPosition).getChildrenList();
                        for (IChild children : list) {
                            children.setChecked(true);
                        }
                    } else {
                        groupBox.setChecked(false);
                        mData.get(groupPosition).setChecked(false);

                        List<IChild> list = (List<IChild>) mData.get(groupPosition).getChildrenList();
                        for (IChild children : list) {
                            children.setChecked(false);
                        }
                    }
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

    static class ViewHolder {
        TextView mParentText, mChildText;
        CheckBox mParentCheckBox, mChildCheckBox;
        ImageView mExpandImage;
    }


}
