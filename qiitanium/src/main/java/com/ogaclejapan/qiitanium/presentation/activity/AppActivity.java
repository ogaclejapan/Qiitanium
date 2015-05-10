package com.ogaclejapan.qiitanium.presentation.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntegerRes;
import android.view.MenuItem;

import com.ogaclejapan.qiitanium.util.Objects;
import com.ogaclejapan.qiitanium.util.ResUtils;

import rx.Subscription;
import rx.subscriptions.SerialSubscription;
import rx.subscriptions.Subscriptions;

abstract class AppActivity extends Activity {

  private final SerialSubscription subscription = new SerialSubscription();
  private FragmentManager fragmentManager;

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
      default:
        return super.onOptionsItemSelected(item);
    }
    return true;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    fragmentManager = getFragmentManager();
  }

  @Override
  protected void onPostCreate(final Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    subscription.set(onBind());
  }

  @Override
  protected void onDestroy() {
    onUnbind();
    super.onDestroy();
  }

  @SuppressWarnings("unchecked")
  protected <T extends Fragment> T findFragmentById(int id) {
    final Fragment f = fragmentManager.findFragmentById(id);
    return (f != null) ? Objects.<T>cast(f) : null;
  }

  protected void setFragment(int id, Fragment fragment) {
    final Fragment f = findFragmentById(id);
    if (f == null) {
      fragmentManager.beginTransaction().add(id, fragment).commit();
    } else {
      fragmentManager.beginTransaction().replace(id, fragment).commit();
    }
  }

  protected Subscription onBind() {
    return Subscriptions.empty();
  }

  protected void onUnbind() {
    subscription.set(Subscriptions.empty());
  }

  protected int getInteger(@IntegerRes int resId) {
    return ResUtils.getInteger(this, resId);
  }

  protected int getColor(@ColorRes int resId) {
    return ResUtils.getColor(this, resId);
  }

  protected float getDimension(@DimenRes int resId) {
    return ResUtils.getDimension(this, resId);
  }

  protected int getDimensionPixelSize(@DimenRes int resId) {
    return ResUtils.getDimensionPixelSize(this, resId);
  }

}
