package com.ufo.widgetdemo.recyclerview.cardview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.ufo.widgetdemo.R;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class RecyclerViewWithCardViewActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private CardViewHolderAdapter mAdapter;
    private List<CardViewModel1> mData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_with_card_view);

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

    private void initData() {

        CardViewModel1 model1 = new CardViewModel1();
        model1.setTitle("Title1");
        model1.setSubhead("2010-01-01");
        model1.setIcon("http://pic2.to8to.com/case/1512/23/20151223_deb3d3b239679ae695faesf5u5l1omv9_sp.jpg");
        model1.setImage("http://pic2.to8to.com/case/1512/23/20151223_deb3d3b239679ae695faesf5u5l1omv9_sp.jpg");
        model1.setContent("第一篇讲解了UI与业务逻辑分层的框架（UIMediator）的使用。本篇将说明该框架的原理及代码实现。 整体结构 UI与后台类绑定主要分为UI输入->后台属性，后台属性-UI更新两部分，为符合依赖倒置原则，分别抽象出IUIToProperty和IPropertyToUI两个接口。");
        mData.add(model1);


        CardViewModel1 model2 = new CardViewModel1();
        model2.setTitle("Title2");
        model2.setSubhead("2010-01-02");
        model2.setIcon("http://pic2.to8to.com/case/1512/23/20151223_3bcb35bc9e31eda80c29457ju0l1j0wf_sp.jpg");
        model2.setImage("http://pic2.to8to.com/case/1512/23/20151223_3bcb35bc9e31eda80c29457ju0l1j0wf_sp.jpg");
        model2.setContent("当HoloLens设备不能识别到自己在世界中的位置时，应用就会发生tracking loss。默认情况下，Unity会暂停Update更新循环并显示一张闪屏图片给用户。当设备重新能追踪到位置时，闪屏图片会消失，并且Update循环还会继续。 此外，用户也可以手动处理这个切换过程。");
        mData.add(model2);


        CardViewModel1 model3 = new CardViewModel1();
        model3.setTitle("Title3");
        model3.setSubhead("2010-01-03");
        model3.setIcon("http://pic2.to8to.com/case/1512/23/20151223_11e56932ea4da3535103okt0jcl1q64c_sp.jpg");
        model3.setImage("http://pic2.to8to.com/case/1512/23/20151223_11e56932ea4da3535103okt0jcl1q64c_sp.jpg");
        model3.setContent("一年不见，博客园都长草啦...... 前几日刚入手新手机小米5，系统真心流畅呀。为啥要买小米5呢，因为要提高生产力呀，好好玩移动前端开发呀哈哈哈 那么问题来了，要怎么调试手机上的前端页面呢？");
        mData.add(model3);

    }

    private void initControl() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_card_view1);

        mAdapter = new CardViewHolderAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mAdapter);
    }


    public static class CardViewModel1 {
        private String icon;
        private String title;
        private String subhead;
        private String image;
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getSubhead() {
            return subhead;
        }

        public void setSubhead(String subhead) {
            this.subhead = subhead;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

    }


    public static class CardViewHolder1 extends RecyclerView.ViewHolder {

        private ImageView mIcon;
        private TextView mTitle;
        private TextView mSubhead;
        private ImageView mImage;
        private TextView mContent;

        private ImageButton mfavored;
        private ImageButton mSend;

        private Button mAction1;
        private Button mAction2;

        public CardViewHolder1(final View itemView) {
            super(itemView);
            mIcon = (ImageView) itemView.findViewById(R.id.icon);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mSubhead = (TextView) itemView.findViewById(R.id.subhead);
            mImage = (ImageView) itemView.findViewById(R.id.bigImage);
            mContent = (TextView) itemView.findViewById(R.id.content);
            mAction1 = (Button) itemView.findViewById(R.id.action1);
            mAction2 = (Button) itemView.findViewById(R.id.action2);
        }


    }


    public class CardViewHolderAdapter extends RecyclerView.Adapter<CardViewHolder1> {
        @Override
        public CardViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(RecyclerViewWithCardViewActivity.this).inflate(R.layout.card_view_item1, parent, false);
            CardViewHolder1 viewHolder = new CardViewHolder1(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CardViewHolder1 holder, int position) {

            int imgWidth = getScreenWidth(RecyclerViewWithCardViewActivity.this);
            int imgHeight = getScreenWidth(RecyclerViewWithCardViewActivity.this) * 9 / 16;

            holder.mTitle.setText(mData.get(position).getTitle());
            holder.mSubhead.setText(mData.get(position).getSubhead());

            holder.mContent.setText(mData.get(position).getContent());

            Glide.with(RecyclerViewWithCardViewActivity.this)
                    .load(mData.get(position).getIcon())
                    .thumbnail(0.1f)
                    .placeholder(R.drawable.ic_placeholder_round)
                    .bitmapTransform(new CenterCrop(RecyclerViewWithCardViewActivity.this),
                            new CropCircleTransformation(RecyclerViewWithCardViewActivity.this))
                    .crossFade()
                    .into(holder.mIcon);


            Glide.with(RecyclerViewWithCardViewActivity.this)
                    .load(mData.get(position).getImage())
                    .thumbnail(0.1f)
                    .placeholder(R.drawable.ic_placeholder)
                    .centerCrop()
                    .override(imgWidth, imgHeight)
                    .crossFade()
                    .into(holder.mImage);

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

    }


    /**
     * 获取屏幕宽度(px)
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }


}
