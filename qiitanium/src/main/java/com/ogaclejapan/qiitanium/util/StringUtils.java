package com.ogaclejapan.qiitanium.util;

import android.text.TextUtils;

public final class StringUtils {

  private StringUtils() {}

  public static String excerpt(String s, int len) {
    if (TextUtils.isEmpty(s)) {
      return s;
    }
    return s.substring(0, Math.min(len, s.length()));
  }
}
