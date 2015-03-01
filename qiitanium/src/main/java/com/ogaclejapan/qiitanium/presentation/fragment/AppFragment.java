package com.ogaclejapan.qiitanium.presentation.fragment;

import com.ogaclejapan.qiitanium.util.ResUtils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntegerRes;

import rx.Subscription;
import rx.subscriptions.SerialSubscription;
import rx.subscriptions.Subscriptions;

abstract class AppFragment extends Fragment {

    private final SerialSubscription mSubscriptions = new SerialSubscription();

    private Context mContext;

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onBind();

        getId();
    }

    @Override
    public void onDestroyView() {
        onUnbind();
        super.onDestroyView();
    }

    protected Context getContext() {
        return mContext;
    }

    protected Subscription onBind() {
        return Subscriptions.empty();
    }

    protected void onUnbind() {
        mSubscriptions.set(Subscriptions.empty());
    }

    protected int getInteger(@IntegerRes int resId) {
        return ResUtils.getInteger(getContext(), resId);
    }

    protected int getColor(@ColorRes int resId) {
        return ResUtils.getColor(getContext(), resId);
    }

    protected float getDimension(@DimenRes int resId) {
        return ResUtils.getDimension(getContext(), resId);
    }

    protected int getDimenstionPixelSize(@DimenRes int resId) {
        return ResUtils.getDimensionPixelSize(getContext(), resId);
    }

}
