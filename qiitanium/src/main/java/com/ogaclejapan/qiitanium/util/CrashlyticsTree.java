package com.ogaclejapan.qiitanium.util;

import android.util.Log;

import com.crashlytics.android.Crashlytics;

import timber.log.Timber;

public class CrashlyticsTree extends Timber.Tree {

  @Override
  protected void log(int priority, String tag, String message, Throwable t) {
    if (priority != Log.ERROR) {
      return;
    }
    Crashlytics.log(message);
    if (t != null) {
      Crashlytics.logException(t);
    }
  }

}
