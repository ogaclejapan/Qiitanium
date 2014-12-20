package com.ogaclejapan.qiitanium.presentation.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class ObservableWebView extends WebView {

    public static interface OnScrollListener {

        void onScrollChanged(int l, int t, int oldl, int oldt);

    }

    private OnScrollListener mOnScrollListener;

    public ObservableWebView(Context context) {
        this(context, null);
    }

    public ObservableWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ObservableWebView(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnScrollListener(OnScrollListener listener) {
        this.mOnScrollListener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollChanged(l, t, oldl, oldt);
        }
    }

}
