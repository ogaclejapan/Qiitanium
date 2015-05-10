package com.ogaclejapan.qiitanium.presentation.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntegerRes;

import com.ogaclejapan.qiitanium.Qiitanium;
import com.ogaclejapan.qiitanium.util.ResUtils;

import rx.Subscription;
import rx.subscriptions.SerialSubscription;
import rx.subscriptions.Subscriptions;

abstract class AppFragment extends Fragment {

  private final SerialSubscription subscription = new SerialSubscription();
  private Context context;

  @Override
  public void onAttach(final Activity activity) {
    super.onAttach(activity);
    context = activity;
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

  @Override public void onDestroy() {
    super.onDestroy();
    Qiitanium.from(getActivity()).getRefWatcher().watch(this);
  }

  protected Context getContext() {
    return context;
  }

  protected Subscription onBind() {
    return Subscriptions.empty();
  }

  protected void onUnbind() {
    subscription.set(Subscriptions.empty());
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
