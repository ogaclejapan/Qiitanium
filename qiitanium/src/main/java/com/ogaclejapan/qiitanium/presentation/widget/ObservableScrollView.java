package com.ogaclejapan.qiitanium.presentation.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ObservableScrollView extends ScrollView {

    public static interface OnScrollListener {

        void onScrollChanged(int l, int t, int oldl, int oldt);

    }

    private OnScrollListener mOnScrollListener;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ObservableScrollView(Context context, AttributeSet attrs,
            int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
