package com.ogaclejapan.qiitanium.presentation.helper;

import android.view.View;

public class ParallaxHeaderHelper {

    private final View mHeaderView;
    private final View mHeaderParallaxView;
    private final int mHeaderMinHeight;
    private final float mHeaderElevation;

    private ParallaxHeaderHelper(Builder builder) {
        mHeaderView = builder.mHeaderView;
        mHeaderParallaxView = builder.mHeaderParallaxView;
        mHeaderMinHeight = builder.mHeaderMinHeight;
        mHeaderElevation = builder.mHeaderElevation;
    }

    public float getElevation() {
        return mHeaderView.getElevation();
    }

    public float scroll(int y) {
        final float flexibleHeight = mHeaderView.getHeight() - mHeaderMinHeight;
        final float ty = Math.min(y, Math.max(flexibleHeight, 0));
        mHeaderView.setTranslationY(-ty);
        mHeaderView.setElevation((ty == flexibleHeight) ? mHeaderElevation : 0f);
        mHeaderParallaxView.setTranslationY(ty / 2f);
        return (ty > 0f) ? ty / flexibleHeight : 0f;
    }

    public int getTranslationY() {
        return (int) Math.abs(mHeaderView.getTranslationY());
    }

    public static class Builder {

        private final View mHeaderView;

        private final View mHeaderParallaxView;

        private int mHeaderMinHeight = 0;

        private float mHeaderElevation = 0f;

        public Builder(View headerView, View headerParallaxView) {
            mHeaderView = headerView;
            mHeaderParallaxView = headerParallaxView;
        }

        public Builder setMinHeight(int height) {
            mHeaderMinHeight = height;
            return this;
        }

        public Builder setElevation(float elevation) {
            mHeaderElevation = elevation;
            return this;
        }

        public ParallaxHeaderHelper create() {
            return new ParallaxHeaderHelper(this);
        }

    }

}
