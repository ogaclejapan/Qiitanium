package com.ogaclejapan.qiitanium.presentation.helper;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.listener.ScrollableTabListener;
import com.ogaclejapan.qiitanium.presentation.widget.ObservableScrollView;
import com.ogaclejapan.qiitanium.presentation.widget.ObservableWebView;
import com.ogaclejapan.qiitanium.util.ResUtils;

import android.app.Activity;

public class ScrollableScrollViewHelper {

    private final ScrollableTabListener mScrollableTabListener;

    private final ObservableScrollView mScrollView;

    private int mHeight;

    private ScrollableScrollViewHelper(Activity activity, ObservableScrollView view, int height) {
        mScrollableTabListener = (activity instanceof ScrollableTabListener)
                ? (ScrollableTabListener) activity
                : null;
        mScrollView = view;
        mHeight = height;

        if (mScrollableTabListener != null) {
            mScrollView.setClipToPadding(false);
            mScrollView.setPadding(0, mHeight, 0, 0);
        }
    }

    public static ScrollableScrollViewHelper with(Activity activity, ObservableScrollView view) {
        int height = ResUtils.getDimensionPixelSize(activity, R.dimen.header_height);
        return with(activity, view, height);
    }

    public static ScrollableScrollViewHelper with(Activity activity, ObservableScrollView view, int height) {
        return new ScrollableScrollViewHelper(activity, view, height);
    }

    public void onScroll(int position) {
        if (mScrollableTabListener != null) {
            mScrollableTabListener.onScroll(getScrollY(), position);
        }
    }

    public void adjustScroll(int y) {
        if (y == 0 || y <= getScrollY()) {
            return;
        }
        mScrollView.setScrollY(y);
    }

    private int getScrollY() {
        return mScrollView.getScrollY();
    }

}
