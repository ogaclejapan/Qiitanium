package com.ogaclejapan.qiitanium.util;

import android.support.v4.widget.SwipeRefreshLayout;

import com.ogaclejapan.rx.binding.RxAction;

public class RxAppActions {

  public static RxAction<SwipeRefreshLayout, Boolean> setRefreshing() {
    return new RxAction<SwipeRefreshLayout, Boolean>() {
      @Override
      public void call(SwipeRefreshLayout swipeRefreshLayout, Boolean refreshing) {
        swipeRefreshLayout.setRefreshing(refreshing);
      }
    };
  }

}
