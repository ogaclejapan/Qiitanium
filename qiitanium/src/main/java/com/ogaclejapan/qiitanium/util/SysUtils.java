package com.ogaclejapan.qiitanium.util;

import android.os.Looper;

public class SysUtils {

  private SysUtils() {
    //No instances.
  }

  public static boolean isMainThread() {
    return (Thread.currentThread() == Looper.getMainLooper().getThread());
  }
}
