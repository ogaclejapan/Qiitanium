package com.ogaclejapan.qiitanium.presentation.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ObservableScrollView extends ScrollView {

  private OnScrollListener onScrollListener;

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
    this.onScrollListener = listener;
  }

  @Override
  protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);

    if (onScrollListener != null) {
      onScrollListener.onScrollChanged(l, t, oldl, oldt);
    }
  }

  public static interface OnScrollListener {

    void onScrollChanged(int l, int t, int oldl, int oldt);

  }

}
