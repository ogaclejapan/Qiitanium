package com.ogaclejapan.qiitanium.presentation.helper;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.listener.ScrollableTabListener;
import com.ogaclejapan.qiitanium.presentation.widget.ObservableWebView;
import com.ogaclejapan.qiitanium.util.ResUtils;

import android.app.Activity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ListView;

public class ScrollableWebViewHelper {

    private final ScrollableTabListener mScrollableTabListener;

    private final ObservableWebView mWebView;

    private int mHeight;

    private ScrollableWebViewHelper(Activity activity, ObservableWebView webView, int height) {
        mScrollableTabListener = (activity instanceof ScrollableTabListener)
                ? (ScrollableTabListener) activity
                : null;
        mWebView = webView;
        mHeight = height;

        if (mScrollableTabListener != null) {
            mWebView.setClipToPadding(false);
            mWebView.setPadding(0, mHeight, 0, 0);
        }
    }

    public static ScrollableWebViewHelper with(Activity activity, ObservableWebView webView) {
        int height = ResUtils.getDimensionPixelSize(activity, R.dimen.article_header_height);
        return with(activity, webView, height);
    }

    public static ScrollableWebViewHelper with(Activity activity, ObservableWebView webView, int height) {
        return new ScrollableWebViewHelper(activity, webView, height);
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
        mWebView.setScrollY(y);
    }

    private int getScrollY() {
        return mWebView.getScrollY();
    }

}
