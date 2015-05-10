package com.ogaclejapan.qiitanium.presentation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import rx.Subscription;
import rx.subscriptions.SerialSubscription;
import rx.subscriptions.Subscriptions;

abstract class AppView<T> extends RelativeLayout {

  private final SerialSubscription subscription = new SerialSubscription();
  private T item;

  protected AppView(final Context context, final AttributeSet attrs) {
    super(context, attrs);
  }

  public final void bindTo(T item) {
    onUnbind();
    this.item = item;
    subscription.set(onBind(item));
  }

  public T getItem() {
    return item;
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

  protected Subscription onBind(T item) {
    return Subscriptions.empty();
  }

  protected void onUnbind() {
    subscription.set(Subscriptions.empty());
  }

}
