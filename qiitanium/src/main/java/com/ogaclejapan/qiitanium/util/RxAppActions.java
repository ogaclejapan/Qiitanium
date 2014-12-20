package com.ogaclejapan.qiitanium.util;

import com.ogaclejapan.rx.binding.RxAction;

import android.support.v4.widget.SwipeRefreshLayout;

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
