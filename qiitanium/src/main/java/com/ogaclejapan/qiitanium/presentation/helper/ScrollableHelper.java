package com.ogaclejapan.qiitanium.presentation.helper;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.listener.ScrollableTabListener;
import com.ogaclejapan.qiitanium.util.ResUtils;

public class ScrollableHelper {

  private final ScrollableTabListener scrollableTabListener;
  private final ListView listView;
  private int height;

  private ScrollableHelper(Activity activity, ListView list, int height) {
    this.scrollableTabListener = (activity instanceof ScrollableTabListener)
        ? (ScrollableTabListener) activity
        : null;
    this.listView = list;
    this.height = height;

    if (scrollableTabListener != null) {
      listView.setClipToPadding(false);
      listView.setPadding(0, this.height, 0, 0);
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
    if (scrollableTabListener != null) {
      scrollableTabListener.onScroll(getScrollY(), position);
    }
  }

  public void adjustScroll(int y) {
    if (y == 0 || y <= getScrollY()) {
      return;
    }
    listView.setSelectionFromTop(0, -y);
  }

  private int getScrollY() {
    View c = listView.getChildAt(0);
    if (c == null) {
      return 0;
    }

    final int totalItemHeight = listView.getFirstVisiblePosition() * c.getHeight();
    return height - c.getTop() + totalItemHeight;
  }

}
