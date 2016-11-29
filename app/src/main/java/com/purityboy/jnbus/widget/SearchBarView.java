package com.purityboy.jnbus.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.purityboy.jnbus.R;
import com.purityboy.jnbus.utils.L;

/**
 * Created by John on 2016/11/28.
 */

public class SearchBarView extends LinearLayout {

    private ImageView searchAction;
    private EditText searchEdit;
    private ImageView searchClear;

    private CharSequence text;
    private OnSearchBarListener l;
    private boolean auto = false;

    public interface OnSearchBarListener {
        //清除
        void clear();
        //搜索
        void search(CharSequence text);
    }

    public SearchBarView(Context context) {
        super(context);
        init(context, null);
    }

    public SearchBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SearchBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context c, AttributeSet attrs) {
        TypedArray array = c.obtainStyledAttributes(attrs, R.styleable.SearchBarView);
        try {
            auto = array.getBoolean(R.styleable.SearchBarView_auto, false);
        } finally {
            array.recycle();
        }
        View view = LayoutInflater.from(c).inflate(R.layout.view_search_bar, this, false);

        searchAction = (ImageView) view.findViewById(R.id.btn_search);
        searchEdit = (EditText) view.findViewById(R.id.edit_keys);
        searchClear = (ImageView) view.findViewById(R.id.btn_clear);

        this.addView(view);

        //TODO 处理重复点击
        searchAction.setOnClickListener(v -> {
            if (null != l)
                l.search(text);});

        searchEdit.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                if (null != l)
                    l.search(text);
                return true;
            }
            return false;
        });

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                text = editable.toString();
                controlClear(!TextUtils.isEmpty(text));
                last = System.currentTimeMillis();
                handler.sendEmptyMessageDelayed(0, 500);
            }
        });

        searchClear.setOnClickListener(v -> {
            if (null != l)
                l.clear();});
    }

    private long last;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (System.currentTimeMillis() - last >= 500) {
                if (auto && null != l && !TextUtils.isEmpty(text)) {      //延时1秒钟
                    l.search(text);
                }
            }
        }
    };

    public void clear() {
        searchEdit.setText("");
    }

    public void setOnSearchBarListener(OnSearchBarListener listener) {
        this.l = listener;
    }

    private void controlClear(boolean isShow) {
        searchClear.setVisibility(isShow ? VISIBLE : GONE);
    }

}
