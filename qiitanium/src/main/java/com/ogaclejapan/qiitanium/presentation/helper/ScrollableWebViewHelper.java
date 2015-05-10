package com.ogaclejapan.qiitanium.presentation.helper;

import android.app.Activity;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.listener.ScrollableTabListener;
import com.ogaclejapan.qiitanium.presentation.widget.ObservableWebView;
import com.ogaclejapan.qiitanium.util.ResUtils;

public class ScrollableWebViewHelper {

  private final ScrollableTabListener scrollableTabListener;
  private final ObservableWebView webView;
  private int height;

  private ScrollableWebViewHelper(Activity activity, ObservableWebView webView, int height) {
    this.scrollableTabListener = (activity instanceof ScrollableTabListener)
        ? (ScrollableTabListener) activity
        : null;
    this.webView = webView;
    this.height = height;

    if (scrollableTabListener != null) {
      this.webView.setClipToPadding(false);
      this.webView.setPadding(0, this.height, 0, 0);
    }
  }

  public static ScrollableWebViewHelper with(Activity activity, ObservableWebView webView) {
    int height = ResUtils.getDimensionPixelSize(activity, R.dimen.article_header_height);
    return with(activity, webView, height);
  }

  public static ScrollableWebViewHelper with(Activity activity, ObservableWebView webView,
      int height) {
    return new ScrollableWebViewHelper(activity, webView, height);
  }

  public void onScroll(int position) {
    if (scrollableTabListener != null) {
      scrollableTabListener.onScroll(getScrollY(), position);
    }
  }

  public void adjustScroll(int y) {
    if (y == 0 || y <= getScrollY()) {
      return;
    }
    webView.setScrollY(y);
  }

  private int getScrollY() {
    return webView.getScrollY();
  }

}
