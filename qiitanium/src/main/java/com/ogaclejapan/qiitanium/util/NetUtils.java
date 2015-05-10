package com.ogaclejapan.qiitanium.util;

import android.text.Html;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.IllegalCharsetNameException;

import timber.log.Timber;

public final class NetUtils {

  private static final String CHARSET_NAME = "UTF-8";

  private NetUtils() {}
  
  public static String encodeURL(String url) {
    try {
      return URLEncoder.encode(url, CHARSET_NAME);
    } catch (UnsupportedEncodingException uee) {
      Timber.e(uee, "Failed to url encode: %s", url);
      throw new IllegalCharsetNameException(CHARSET_NAME);
    }
  }

  public static String decodeURL(String url) {
    try {
      return URLDecoder.decode(url, CHARSET_NAME);
    } catch (UnsupportedEncodingException uee) {
      Timber.e(uee, "Failed to url decode: %s", url);
      throw new IllegalCharsetNameException(CHARSET_NAME);
    }
  }

  public static String escapeHtml(CharSequence text) {
    return Html.escapeHtml(text);
  }

  public static String unescapeHtml(String text) {
    return Html.fromHtml(text).toString();
  }

}
