package com.ufo.widgetdemo.recyclerview.chat;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ufo.widgetdemo.R;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecyclerViewWithChatActivity extends AppCompatActivity {

    private static final String KEY_CHAT = "KEY_CHAT";
    private static final String KEY_POSITION = "KEY_POSITION";

    private static final String PORTRAIT_HOST = "http://pic2.to8to.com/case/1512/23/20151223_deb3d3b239679ae695faesf5u5l1omv9_sp.jpg";
    private static final String PORTRAIT_GUEST = "http://pic2.to8to.com/case/1512/23/20151223_3bcb35bc9e31eda80c29457ju0l1j0wf_sp.jpg";

    private RecyclerView mRecyclerView;
    private RecyclerViewWithChatAdapter mAdapter;
    private List<ChatModel> mData;

    private ImageButton mImageButtonSend;
    private EditText mEditText;

    private View mRoot;
    private int mRootBottom = Integer.MIN_VALUE;

    private MyHandler mHandler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_with_chat);

        initData();
        initRecyclerView();
        initControl();

    }


    private void initData() {
        mData = new ArrayList<>();

        ChatModel chatModel1 = new ChatModel();
        chatModel1.setContent("因公司业务需要，需要将原有的ERP系统加上支持繁体语言，但不能改变原有的编码方式，即：普通程序员感受不到编码有什么不同。经过我与几个同事的多番沟通，确定了以下两种方案： 方案一：在窗体基类中每次加载并显示窗体时，会自动递归遍历含文本显示的控件");
        chatModel1.setChatType(ChatType.Guest);
        chatModel1.setDataType(DataType.Text);
        chatModel1.setHeadPortrait(PORTRAIT_GUEST);
        try {
            chatModel1.setDate(string2date("2016-10-01 10:00:00", "yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mData.add(chatModel1);

        ChatModel chatModel2 = new ChatModel();
        chatModel2.setContent("测试一下2");
        chatModel2.setChatType(ChatType.Host);
        chatModel2.setDataType(DataType.Text);
        chatModel2.setHeadPortrait(PORTRAIT_HOST);
        try {
            chatModel2.setDate(string2date("2016-10-01 10:10:00", "yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mData.add(chatModel2);

        ChatModel chatModel3 = new ChatModel();
        chatModel3.setContent("http://pic2.to8to.com/case/1512/23/20151223_deb3d3b239679ae695faesf5u5l1omv9_sp.jpg");
        chatModel3.setChatType(ChatType.Guest);
        chatModel3.setDataType(DataType.Image);
        chatModel3.setHeadPortrait(PORTRAIT_GUEST);
        try {
            chatModel3.setDate(string2date("2016-10-01 11:00:00", "yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mData.add(chatModel3);

        ChatModel chatModel4 = new ChatModel();
        chatModel4.setContent("测试一下4");
        chatModel4.setChatType(ChatType.Guest);
        chatModel4.setDataType(DataType.Text);
        chatModel4.setHeadPortrait(PORTRAIT_GUEST);
        try {
            chatModel4.setDate(string2date("2016-10-02 08:00:00", "yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mData.add(chatModel4);


        ChatModel chatModel5 = new ChatModel();
        chatModel5.setContent("http://pic2.to8to.com/case/1512/23/20151223_11e56932ea4da3535103okt0jcl1q64c_sp.jpg");
        chatModel5.setChatType(ChatType.Host);
        chatModel5.setDataType(DataType.Image);
        chatModel5.setHeadPortrait(PORTRAIT_HOST);
        try {
            chatModel5.setDate(string2date("2016-10-02 08:03:00", "yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mData.add(chatModel5);

    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_chat);

        mAdapter = new RecyclerViewWithChatAdapter(mData, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mAdapter);

    }

    private void initControl() {

        mRoot = findViewById(R.id.root);
        mRoot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                mRoot.getGlobalVisibleRect(r);
                // 进入Activity时会布局，第一次调用onGlobalLayout，先记录开始软键盘没有弹出时底部的位置
                if (mRootBottom == Integer.MIN_VALUE) {
                    mRootBottom = r.bottom;
                    return;
                }
                // adjustResize，软键盘弹出后高度会变小
                if (r.bottom < mRootBottom) {
                    mRecyclerView.smoothScrollToPosition(mData.size());
                }
            }
        });


        mEditText = (EditText) findViewById(R.id.edit_message);
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    sendText();
                }
                return true;
            }
        });


        mImageButtonSend = (ImageButton) findViewById(R.id.btn_send);
        mImageButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendText();
            }
        });

    }


    private void sendText() {
        String content = mEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(content)) {

            ChatModel chatModel = new ChatModel();
            chatModel.setContent(content);
            chatModel.setChatType(ChatType.Host);
            chatModel.setDataType(DataType.Text);
            chatModel.setHeadPortrait(PORTRAIT_HOST);
            chatModel.setSendStatusType(SendStatusType.Sending);
            chatModel.setDate(new Date());
            mData.add(chatModel);

            int position = mData.size() - 1;
            mAdapter.notifyItemInserted(position);
            mRecyclerView.smoothScrollToPosition(position);

            mEditText.getText().clear();

            Message msg = new Message();

            Bundle bundle = new Bundle();
            bundle.putInt(KEY_POSITION, position);
            bundle.putParcelable(KEY_CHAT, chatModel);
            msg.what = 0;
            msg.setData(bundle);
            mHandler.sendMessageDelayed(msg, 2000);

        }
    }


    private void doSendText(ChatModel chatModel, int position) {

//        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);//系统自带提示音
//        Ringtone rt = RingtoneManager.getRingtone(getApplicationContext(), uri);
//        rt.play();


        chatModel.setSendStatusType(SendStatusType.Sended);
        mAdapter.notifyItemChanged(position);

    }


    private static class MyHandler extends Handler {

        private final WeakReference<RecyclerViewWithChatActivity> mActivity;

        public MyHandler(RecyclerViewWithChatActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            RecyclerViewWithChatActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 0:
                        ChatModel chatModel = msg.getData().getParcelable(KEY_CHAT);
                        int position = msg.getData().getInt(KEY_POSITION);
                        activity.doSendText(chatModel, position);
                        break;
                }
            }
        }
    }


    //触摸屏幕隐藏键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                mEditText.clearFocus();
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    //键盘可否隐藏
    private boolean isShouldHideInput(View v, MotionEvent event) {

        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            View t = findViewById(R.id.toolbar);
            t.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + t.getHeight(), right = left
                    + t.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    //隐藏键盘
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public static Date string2date(String date, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(date);
    }


}
