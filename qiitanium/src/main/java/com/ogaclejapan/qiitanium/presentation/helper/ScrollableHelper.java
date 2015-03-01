package com.ogaclejapan.qiitanium.presentation.helper;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.listener.ScrollableTabListener;
import com.ogaclejapan.qiitanium.util.ResUtils;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

public class ScrollableHelper {

    private final ScrollableTabListener mScrollableTabListener;

    private final ListView mListView;

    private int mHeight;

    private ScrollableHelper(Activity activity, ListView list, int height) {
        mScrollableTabListener = (activity instanceof ScrollableTabListener)
                ? (ScrollableTabListener) activity
                : null;
        mListView = list;
        mHeight = height;

        if (mScrollableTabListener != null) {
            mListView.setClipToPadding(false);
            mListView.setPadding(0, mHeight, 0, 0);
        }
    }

    public static ScrollableHelper with(Activity activity, ListView list) {
        int height = ResUtils.getDimensionPixelSize(activity, R.dimen.header_height);
        return with(activity, list, height);
    }

    public static ScrollableHelper with(Activity activity, ListView list, int height) {
        return new ScrollableHelper(activity, list, height);
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
        mListView.setSelectionFromTop(0, -y);
    }

    private int getScrollY() {
        View c = mListView.getChildAt(0);
        if (c == null) {
            return 0;
        }

        final int totalItemHeight = mListView.getFirstVisiblePosition() * c.getHeight();
        return mHeight - c.getTop() + totalItemHeight;
    }

}
