package com.ogaclejapan.qiitanium.util;

import com.crashlytics.android.Crashlytics;

import timber.log.Timber;

public class CrashlyticsTree extends Timber.HollowTree {

  @Override
  public void e(String message, Object... args) {
    Crashlytics.log(String.format(message, args));
  }

  @Override
  public void e(Throwable t, String message, Object... args) {
    Crashlytics.logException(t);
    Crashlytics.log(String.format(message, args));
  }

}
