package com.ogaclejapan.qiitanium.presentation.fragment;

import com.ogaclejapan.qiitanium.R;

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

import rx.Subscription;
import rx.subscriptions.SerialSubscription;
import rx.subscriptions.Subscriptions;

abstract class AppDialogFragment extends DialogFragment {

    private final SerialSubscription mSubscriptions = new SerialSubscription();
    private final String mTag;

    private Context mContext;

    protected AppDialogFragment(String tag) {
        super();
        mTag = tag;
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        mContext = activity;
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
        mSubscriptions.set(onBind());
    }

    @Override
    public void onDestroyView() {
        onUnbind();
        super.onDestroyView();
    }

    public void show(FragmentManager fm) {
        show(fm, mTag);
    }

    public void show(FragmentTransaction ft) {
        show(ft, mTag);
    }

    protected Context getContext() {
        return mContext;
    }

    protected abstract View onSetupView(LayoutInflater inflater, Bundle savedInstanceState);

    protected Subscription onBind() {
        return Subscriptions.empty();
    }

    protected void onUnbind() {
        mSubscriptions.set(Subscriptions.empty());
    }


}
