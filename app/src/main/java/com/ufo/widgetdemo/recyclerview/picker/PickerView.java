package com.ufo.widgetdemo.recyclerview.picker;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.ufo.widgetdemo.R;

/**
 * Created by tjpld on 2016/10/25.
 */

public class PickerView extends ViewGroup implements AdapterView.OnItemClickListener {

    private final static String DEFAULT_SEARCH_HINT = "搜索";
    private final static int DEFAULT_SEARCH_TEXT_SIZE = 14;
    private final static int DEFAULT_SEARCH_PADDING = 10;

    private Context mContext;

    private String searchHint;
    private float searchTextSize = DEFAULT_SEARCH_TEXT_SIZE;
    private int searchPadding = DEFAULT_SEARCH_PADDING;


    private HorizontalScrollView mHorizontalScrollView;
    private LinearLayout mLinearLayout;

    private AutoCompleteTextView mAutoCompleteTextView;

    private boolean isFirst = true;
    private boolean isFirstFocus = true;

    private PickerViewAdapter mAdapter;
    private DataSetObserver mDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            onDataChange();
        }

    };

    private void onDataChange() {

        if (mAdapter == null) return;

        mLinearLayout.removeAllViews();

        int count = getItemCount();

        for (int i = 0; i < count; i++) {
            View view = mAdapter.onMakeView(mAdapter.getItem(i));
            mLinearLayout.addView(view);

            final int position = i;

            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapter.delItem(position);
                }
            });
        }

        mHorizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                mHorizontalScrollView.fullScroll(ScrollView.FOCUS_RIGHT);
            }
        });


    }


    public PickerView(Context context) {
        super(context);
        init(context);
    }

    public PickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadAttributes(context, attrs);
        init(context);
    }

    public PickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        loadAttributes(context, attrs);
        init(context);
    }


    private void loadAttributes(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes
                    (attrs, R.styleable.PickerView);
            searchHint = typedArray.getString(R.styleable.PickerView_searchHint);
            searchTextSize = typedArray.getDimensionPixelSize(R.styleable.PickerView_searchTextSize, DEFAULT_SEARCH_TEXT_SIZE);
            searchPadding = typedArray.
                    getDimensionPixelSize(R.styleable.PickerView_searchPadding, DEFAULT_SEARCH_PADDING);
            if (typedArray != null) {
                typedArray.recycle();
            }
        }

        if (TextUtils.isEmpty(searchHint)) searchHint = DEFAULT_SEARCH_HINT;
    }


    private void init(Context context) {

        this.mContext = context;

        this.setFocusable(true);
        this.setFocusableInTouchMode(true);

        mAutoCompleteTextView = new AutoCompleteTextView(context);
        LayoutParams edit_params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mAutoCompleteTextView.setLayoutParams(edit_params);
        mAutoCompleteTextView.setHint(searchHint);
        mAutoCompleteTextView.setMaxLines(1);
        mAutoCompleteTextView.setSingleLine(true);
        mAutoCompleteTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, searchTextSize);
        mAutoCompleteTextView.setBackgroundColor(Color.TRANSPARENT);
        mAutoCompleteTextView.setPadding(searchPadding, searchPadding, searchPadding, searchPadding);
        mAutoCompleteTextView.setThreshold(1);
        mAutoCompleteTextView.setOnItemClickListener(this);
        mAutoCompleteTextView.setDropDownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (isFirstFocus) {
                    if (mAdapter != null) {
                        mAdapter.onLayoutDropDown();
                    }
                    isFirstFocus = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        this.addView(mAutoCompleteTextView);


        mLinearLayout = new LinearLayout(context);
        mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        LayoutParams linear_params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mLinearLayout.setLayoutParams(linear_params);

        mHorizontalScrollView = new HorizontalScrollView(context);
        LayoutParams scroll_params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mHorizontalScrollView.setLayoutParams(scroll_params);

        mHorizontalScrollView.addView(mLinearLayout);

        this.addView(mHorizontalScrollView);

        mAutoCompleteTextView.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                    String content = mAutoCompleteTextView.getText().toString();
                    if (TextUtils.isEmpty(content)) {
                        if (isLastItemSelected()) {
                            mAdapter.delLastItem();
                            if (mAdapter.getCount() == 0) {
                                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                            }
                        }
                        return true;
                    }
                }
                return false;
            }
        });

    }

    private boolean isLastItemSelected() {
        int count = mLinearLayout.getChildCount();
        if (count > 0) {
            View view = mLinearLayout.getChildAt(count - 1);
            if (view.isSelected()) {
                return true;
            } else {
                view.setSelected(true);
                return false;
            }
        }

        return false;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);

        LayoutParams lp_edit = mAutoCompleteTextView.getLayoutParams();

        int childWidthMeasureSpec_edit = getChildMeasureSpec(widthMeasureSpec,
                0, lp_edit.width);
        int childHeightMeasureSpec_edit = getChildMeasureSpec(heightMeasureSpec,
                0, lp_edit.height);

        mAutoCompleteTextView.measure(childWidthMeasureSpec_edit, childHeightMeasureSpec_edit);

        if (isFirst) {
            isFirst = false;
            mAutoCompleteTextView.setMaxWidth(mAutoCompleteTextView.getMeasuredWidth());
        }

        LayoutParams lp_scroll = mHorizontalScrollView.getLayoutParams();

        int childWidthMeasureSpec_scroll = getChildMeasureSpec(widthMeasureSpec,
                0, lp_scroll.width);
        int childHeightMeasureSpec_scroll = getChildMeasureSpec(heightMeasureSpec,
                0, lp_scroll.height);

        mHorizontalScrollView.measure(childWidthMeasureSpec_scroll, childHeightMeasureSpec_scroll);

        int height = Math.max(mAutoCompleteTextView.getMeasuredHeight(), mHorizontalScrollView.getMeasuredHeight());

        setMeasuredDimension(width, getPaddingTop() + getPaddingBottom() + height);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int paddingTop = getPaddingTop();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int scrollViewWidth = mHorizontalScrollView.getMeasuredWidth();
        int scrollViewHeight = mHorizontalScrollView.getMeasuredHeight();
        int editTextWidth = mAutoCompleteTextView.getMeasuredWidth();
        int editTextHeight = mAutoCompleteTextView.getMeasuredHeight();


        if (scrollViewWidth >= width - editTextWidth - paddingLeft - paddingRight) {
            mHorizontalScrollView.layout(paddingLeft, paddingTop, width - editTextWidth - paddingLeft, paddingTop + scrollViewHeight);
            int top = (height - editTextHeight) / 2;
            mAutoCompleteTextView.layout(width - editTextWidth - paddingLeft, top, width - paddingRight, top + editTextHeight);
        } else {
            mHorizontalScrollView.layout(paddingLeft, paddingTop, paddingLeft + scrollViewWidth, paddingTop + scrollViewHeight);
            int top = (height - editTextHeight) / 2;
            mAutoCompleteTextView.layout(paddingLeft + scrollViewWidth, top, paddingLeft + scrollViewWidth + editTextWidth, top + editTextHeight);
        }

    }


    public int getItemCount() {
        if (this.mAdapter != null) {
            return this.mAdapter.getCount();
        } else return 0;
    }

    public void setAdapter(PickerViewAdapter adapter) {
        this.mAdapter = adapter;
        mAutoCompleteTextView.setAdapter(mAdapter.getPickerViewSearchAdapter());
        mAdapter.registerObserver(mDataSetObserver);
    }


    public AutoCompleteTextView getmAutoCompleteTextView() {
        return mAutoCompleteTextView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mAdapter != null) {
            mAutoCompleteTextView.setText(null);
            PickerViewSearchAdapter pickerViewSearchAdapter = (PickerViewSearchAdapter) mAutoCompleteTextView.getAdapter();
            int pos = pickerViewSearchAdapter.getDataSourcePositionInQuery(position);
            mAdapter.dropDownOnItemClick(parent, view, pos, id);
        }
    }
}
