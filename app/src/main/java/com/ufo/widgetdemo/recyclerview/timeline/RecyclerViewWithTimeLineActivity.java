package com.ufo.widgetdemo.recyclerview.timeline;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;


import com.ufo.widgetdemo.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewWithTimeLineActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_with_time_line);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_time_line);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        TimeLineAdapter adapter = new TimeLineAdapter(getData());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);

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




    private List<TimeLineModel> getData() {
        List<TimeLineModel> models = new ArrayList<>();

        models.add(new TimeLineModel("XiaoMing", 21));
        models.add(new TimeLineModel("XiaoFang", 20));
        models.add(new TimeLineModel("XiaoHua", 25));
        models.add(new TimeLineModel("XiaoA", 22));
        models.add(new TimeLineModel("XiaoNiu", 23));
        models.add(new TimeLineModel("XiaoMing", 21));
        models.add(new TimeLineModel("XiaoFang", 20));
        models.add(new TimeLineModel("XiaoHua", 25));
        models.add(new TimeLineModel("XiaoA", 22));
        models.add(new TimeLineModel("XiaoNiu", 23));
        models.add(new TimeLineModel("XiaoMing", 21));
        models.add(new TimeLineModel("XiaoFang", 20));
        models.add(new TimeLineModel("XiaoHua", 25));
        models.add(new TimeLineModel("XiaoA", 22));
        models.add(new TimeLineModel("XiaoNiu", 23));
        models.add(new TimeLineModel("XiaoMing", 21));
        models.add(new TimeLineModel("XiaoFang", 20));
        models.add(new TimeLineModel("XiaoHua", 25));
        models.add(new TimeLineModel("XiaoA", 22));
        models.add(new TimeLineModel("XiaoNiu", 23));

        return models;
    }

}
