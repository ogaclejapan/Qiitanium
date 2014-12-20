package com.ogaclejapan.qiitanium.presentation.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

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

}
