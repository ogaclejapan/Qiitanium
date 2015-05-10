package com.ogaclejapan.qiitanium.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class IOUtils {

  private IOUtils() {}

  public static String readAllFromAssets(Context context, String target) throws IOException {
    AssetManager as = context.getApplicationContext().getResources().getAssets();

    StringBuilder sb = new StringBuilder();

    InputStream is = null;
    BufferedReader br = null;
    try {
      is = as.open(target);
      br = new BufferedReader(new InputStreamReader(is));

      String s;
      while ((s = br.readLine()) != null) {
        sb.append(s).append("\n");
      }
    } finally {
      closeQuietly(br);
      closeQuietly(is);
    }

    return sb.toString();
  }

  public static void closeQuietly(Closeable closeable) {
    if (closeable == null) {
      return;
    }
    try {
      closeable.close();
    } catch (IOException ignore) {
      //Do nothing
    }
  }

}
