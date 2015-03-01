package com.ogaclejapan.qiitanium.presentation.helper;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.ogaclejapan.qiitanium.R;

import android.content.Context;
import android.support.annotation.StringRes;

public class SnackbarHelper {

    private final Context mContext;

    private SnackbarHelper(Context context) {
        mContext = context;
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
                Snackbar.with(mContext)
                        .position(position)
                        .text(resId)
                        .actionLabel(R.string.close));
    }


}
