package com.ufo.widgetdemo.recyclerview.sticky;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.turingtechnologies.materialscrollbar.AlphabetIndicator;
import com.turingtechnologies.materialscrollbar.DragScrollBar;
import com.ufo.widgetdemo.DataModel;
import com.ufo.widgetdemo.R;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class StickyListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private StickyListAdapter mAdapter;
    private List<DataModel> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_list);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initData();
        initControl();

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


    private void initControl(){
        mRecyclerView = (RecyclerView) findViewById(R.id.sticky_header_recyclerview);

        mAdapter = new StickyListAdapter(mData, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);


        //表头
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mAdapter);
        mRecyclerView.addItemDecoration(headersDecor);

        mRecyclerView.setAdapter(mAdapter);

        //索引
        ((DragScrollBar) findViewById(R.id.dragScrollBar)).setIndicator(new AlphabetIndicator(this), true);
    }


    private void initData(){
        mData.add(new DataModel("Apple", null, null));
        mData.add(new DataModel("Ameircan", null, null));
        mData.add(new DataModel("Affica", null, null));
        mData.add(new DataModel("Banana", null, null));
        mData.add(new DataModel("Bee", null, null));
        mData.add(new DataModel("Card", null, null));
        mData.add(new DataModel("Cool", null, null));
        mData.add(new DataModel("Deep", null, null));
        mData.add(new DataModel("Disk", null, null));
        mData.add(new DataModel("Drag", null, null));
        mData.add(new DataModel("Different", null, null));
        mData.add(new DataModel("Duck", null, null));
        mData.add(new DataModel("DVD", null, null));
        mData.add(new DataModel("Dig", null, null));
        mData.add(new DataModel("Dog", null, null));
        mData.add(new DataModel("Egg", null, null));
        mData.add(new DataModel("EVA", null, null));
        mData.add(new DataModel("Euroure", null, null));
        mData.add(new DataModel("Elect", null, null));
        mData.add(new DataModel("Good", null, null));
        mData.add(new DataModel("Google", null, null));
        mData.add(new DataModel("Guess", null, null));
        mData.add(new DataModel("Height", null, null));
        mData.add(new DataModel("High", null, null));
        mData.add(new DataModel("Admin", null, null));
        mData.add(new DataModel("Again", null, null));
        mData.add(new DataModel("Blue", null, null));
        mData.add(new DataModel("Color", null, null));


        Collections.sort(mData, new Comparator<DataModel>() {
            @Override
            public int compare(DataModel lhs, DataModel rhs) {
                String s1 = lhs.getTitle();
                String s2 = rhs.getTitle();
                return Collator.getInstance(Locale.ENGLISH).compare(s1, s2);
            }
        });
    }

}
