package com.ogaclejapan.qiitanium.presentation.helper;

import android.widget.AbsListView;
import android.widget.ListAdapter;

public class LoadMoreHelper {

  private static final int DEFAULT_LOAD_MORE_LEFTOVER_COUNT = 10;

  private final AbsListView listView;
  private final int leftoverCount;

  protected LoadMoreHelper(AbsListView listView, int leftoverCount) {
    this.listView = listView;
    this.leftoverCount = leftoverCount;
  }

  public static LoadMoreHelper with(AbsListView listView) {
    return with(listView, DEFAULT_LOAD_MORE_LEFTOVER_COUNT);
  }

  public static LoadMoreHelper with(AbsListView listView, int leftoverCount) {
    return new LoadMoreHelper(listView, leftoverCount);
  }

  public boolean onNext(int currentItemCount, int totalItemCount) {

    final ListAdapter adapter = listView.getAdapter();
    if (adapter == null || adapter.isEmpty()) {
      return false;
    }

    return ((totalItemCount - currentItemCount) <= leftoverCount);
  }

}
