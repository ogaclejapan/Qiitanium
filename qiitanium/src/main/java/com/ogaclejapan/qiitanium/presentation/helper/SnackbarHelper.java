package com.ogaclejapan.qiitanium.presentation.helper;

import android.content.Context;
import android.support.annotation.StringRes;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.ogaclejapan.qiitanium.R;

public class SnackbarHelper {

  private final Context context;

  private SnackbarHelper(Context context) {
    this.context = context;
  }

  public static SnackbarHelper with(Context context) {
    return new SnackbarHelper(context);
  }

  public void showTop(@StringRes int resId) {
    show(resId, Snackbar.SnackbarPosition.TOP);
  }

  public void showButtom(@StringRes int resId) {
    show(resId, Snackbar.SnackbarPosition.BOTTOM);
  }

  private void show(@StringRes int resId, Snackbar.SnackbarPosition position) {
    SnackbarManager.show(
        Snackbar.with(context)
            .position(position)
            .text(resId)
            .actionLabel(R.string.close));
  }

}
