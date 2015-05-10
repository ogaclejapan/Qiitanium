package com.ogaclejapan.qiitanium.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public final class IntentUtils {

  private IntentUtils() {}

  public static void openExternalUrl(Context context, String url) {
    Uri uri = Uri.parse(url);
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }

  public static void share(final Context context, final String url, final String title) {
    Intent intent = shareIntent(url, title);
    context.startActivity(intent);
  }

  public static Intent shareIntent(final String url, final String title) {
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("text/plain");
    intent.putExtra(Intent.EXTRA_TEXT, url);
    intent.putExtra(Intent.EXTRA_SUBJECT, title);
    return intent;
  }

}
