package com.ogaclejapan.qiitanium.presentation.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.ogaclejapan.qiitanium.R;

import rx.Subscription;
import rx.subscriptions.SerialSubscription;
import rx.subscriptions.Subscriptions;

abstract class AppDialogFragment extends DialogFragment {

  private final SerialSubscription subscription = new SerialSubscription();
  private final String tag;
  private Context context;

  protected AppDialogFragment(String tag) {
    super();
    this.tag = tag;
  }

  @Override
  public void onAttach(final Activity activity) {
    super.onAttach(activity);
    context = activity;
  }

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppDialog);
  }

  @Override
  public Dialog onCreateDialog(final Bundle savedInstanceState) {
    return new AlertDialog.Builder(getActivity(), getTheme())
        .setPositiveButton(R.string.close, null)
        .setView(onSetupView(LayoutInflater.from(getContext()), savedInstanceState))
        .create();
  }

  @Override
  public void onActivityCreated(final Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    subscription.set(onBind());
  }

  @Override
  public void onDestroyView() {
    onUnbind();
    super.onDestroyView();
  }

  public void show(FragmentManager fm) {
    show(fm, tag);
  }

  public void show(FragmentTransaction ft) {
    show(ft, tag);
  }

  protected Context getContext() {
    return context;
  }

  protected abstract View onSetupView(LayoutInflater inflater, Bundle savedInstanceState);

  protected Subscription onBind() {
    return Subscriptions.empty();
  }

  protected void onUnbind() {
    subscription.set(Subscriptions.empty());
  }

}
