package com.ogaclejapan.qiitanium.presentation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import rx.Subscription;
import rx.subscriptions.SerialSubscription;
import rx.subscriptions.Subscriptions;

abstract class AppView<T> extends RelativeLayout {

    private final SerialSubscription mSubscriptions = new SerialSubscription();

    private T mItem;

    protected AppView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public final void bindTo(T item) {
        onUnbind();
        mItem = item;
        mSubscriptions.set(onBind(item));
    }

    public T getItem() {
        return mItem;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            onViewCreated(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        onUnbind();
        super.onDetachedFromWindow();
    }

    protected abstract void onViewCreated(View view);

    protected abstract Subscription onBind(T item);

    protected void onUnbind() {
        mSubscriptions.set(Subscriptions.empty());
    }

}
