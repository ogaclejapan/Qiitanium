package com.ogaclejapan.qiitanium.presentation.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquareImageView extends ImageView {

  public SquareImageView(final Context context) {
    super(context);
  }

  public SquareImageView(final Context context, final AttributeSet attrs) {
    super(context, attrs);
  }

  public SquareImageView(final Context context, final AttributeSet attrs,
      final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public SquareImageView(final Context context, final AttributeSet attrs,
      final int defStyleAttr, final int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
    final int w = MeasureSpec.getSize(widthMeasureSpec);
    final int h = MeasureSpec.getSize(heightMeasureSpec);
    final int minSize = Math.min(w, h);
    final int squareMeasureSpec = MeasureSpec.makeMeasureSpec(minSize, MeasureSpec.EXACTLY);
    super.onMeasure(squareMeasureSpec, squareMeasureSpec);
  }

}
