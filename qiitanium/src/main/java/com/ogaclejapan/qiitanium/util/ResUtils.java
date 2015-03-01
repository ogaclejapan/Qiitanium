package com.ogaclejapan.qiitanium.util;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.PluralsRes;
import android.support.annotation.StringRes;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public final class ResUtils {

    public static int getInteger(Context ctx, @IntegerRes int integerResId) {
        return ctx.getResources().getInteger(integerResId);
    }

    public static String getString(Context ctx, @StringRes int stringResId) {
        return ctx.getString(stringResId);
    }

    public static String getString(Context ctx, @StringRes int stringResId, Object... formatArgs) {
        return ctx.getString(stringResId, formatArgs);
    }

    public static String getQuantityString(Context ctx, @PluralsRes int pluralResId, int quantity) {
        return ctx.getResources().getQuantityString(pluralResId, quantity, quantity);
    }

    public static int getColor(Context ctx, @ColorRes int colorResId) {
        return ctx.getResources().getColor(colorResId);
    }

    public static float getDimension(Context ctx, @DimenRes int dimenResId) {
        return ctx.getResources().getDimension(dimenResId);
    }

    public static int getDimensionPixelSize(Context ctx, @DimenRes int dimenResId) {
        return ctx.getResources().getDimensionPixelSize(dimenResId);
    }

    public static float pxToSp(Context ctx, int px) {
        return pxToSp(getDisplayMetrics(ctx), px);
    }

    public static float pxToSp(DisplayMetrics dm, int px) {
        return applyDimension(TypedValue.COMPLEX_UNIT_SP, px, dm);
    }

    public static float pxToDp(Context ctx, int px) {
        return pxToDp(getDisplayMetrics(ctx), px);
    }

    public static float pxToDp(DisplayMetrics dm, int px) {
        return applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, dm);
    }

    public static DisplayMetrics getDisplayMetrics(Context ctx) {
        return ctx.getResources().getDisplayMetrics();
    }

    private static float applyDimension(int unit, float value, DisplayMetrics dm) {
        return TypedValue.applyDimension(unit, value, dm);
    }

    private ResUtils() {
        //No instances
    }
}
