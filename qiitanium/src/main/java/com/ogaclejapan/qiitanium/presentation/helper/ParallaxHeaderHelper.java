package com.ogaclejapan.qiitanium.presentation.helper;

import android.view.View;

public class ParallaxHeaderHelper {

  private final View headerView;
  private final View headerParallaxView;
  private final int headerMinHeight;
  private final float headerElevation;

  private ParallaxHeaderHelper(Builder builder) {
    this.headerView = builder.headerView;
    this.headerParallaxView = builder.headerParallaxView;
    this.headerMinHeight = builder.headerMinHeight;
    this.headerElevation = builder.headerElevation;
  }

  public float getElevation() {
    return headerView.getElevation();
  }

  public float scroll(int y) {
    final float flexibleHeight = headerView.getHeight() - headerMinHeight;
    final float ty = Math.min(y, Math.max(flexibleHeight, 0));
    headerView.setTranslationY(-ty);
    headerView.setElevation((ty == flexibleHeight) ? headerElevation : 0f);
    headerParallaxView.setTranslationY(ty / 2f);
    return (ty > 0f) ? ty / flexibleHeight : 0f;
  }

  public int getTranslationY() {
    return (int) Math.abs(headerView.getTranslationY());
  }

  public static class Builder {

    private final View headerView;
    private final View headerParallaxView;
    private int headerMinHeight = 0;
    private float headerElevation = 0f;

    public Builder(View headerView, View headerParallaxView) {
      this.headerView = headerView;
      this.headerParallaxView = headerParallaxView;
    }

    public Builder setMinHeight(int height) {
      headerMinHeight = height;
      return this;
    }

    public Builder setElevation(float elevation) {
      headerElevation = elevation;
      return this;
    }

    public ParallaxHeaderHelper create() {
      return new ParallaxHeaderHelper(this);
    }

  }

}
