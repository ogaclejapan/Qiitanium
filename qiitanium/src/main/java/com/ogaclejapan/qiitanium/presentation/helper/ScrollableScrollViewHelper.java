package com.ogaclejapan.qiitanium.presentation.helper;

import android.app.Activity;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.listener.ScrollableTabListener;
import com.ogaclejapan.qiitanium.presentation.widget.ObservableScrollView;
import com.ogaclejapan.qiitanium.util.ResUtils;

public class ScrollableScrollViewHelper {

  private final ScrollableTabListener scrollableTabListener;
  private final ObservableScrollView scrollView;
  private int height;

  private ScrollableScrollViewHelper(Activity activity, ObservableScrollView view, int height) {
    this.scrollableTabListener = (activity instanceof ScrollableTabListener)
        ? (ScrollableTabListener) activity
        : null;
    this.scrollView = view;
    this.height = height;

    if (scrollableTabListener != null) {
      scrollView.setClipToPadding(false);
      scrollView.setPadding(0, this.height, 0, 0);
    }
  }

  public static ScrollableScrollViewHelper with(Activity activity, ObservableScrollView view) {
    int height = ResUtils.getDimensionPixelSize(activity, R.dimen.header_height);
    return with(activity, view, height);
  }

  public static ScrollableScrollViewHelper with(Activity activity, ObservableScrollView view,
      int height) {
    return new ScrollableScrollViewHelper(activity, view, height);
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
    scrollView.setScrollY(y);
  }

  private int getScrollY() {
    return scrollView.getScrollY();
  }

}
