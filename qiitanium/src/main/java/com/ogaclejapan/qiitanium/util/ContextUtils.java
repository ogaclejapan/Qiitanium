package com.ogaclejapan.qiitanium.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class ContextUtils {

    public static int getInteger(Context context, int integerResId) {
        return context.getResources().getInteger(integerResId);
    }

    public static String getString(Context context, int stringResId) {
        return context.getString(stringResId);
    }

    public static String getString(Context context, int stringResId, Object... formatArgs) {
        return context.getString(stringResId, formatArgs);
    }

    public static String getQuantityString(Context context, int pluralResId, int quantity) {
        return context.getResources().getQuantityString(pluralResId, quantity, quantity);
    }

    public static int getColor(Context context, int colorResId) {
        return context.getResources().getColor(colorResId);
    }

    public static float getDimension(Context context, int dimenResId) {
        return context.getResources().getDimension(dimenResId);
    }

    public static int getDimensionPixelSize(Context context, int dimenResId) {
        return context.getResources().getDimensionPixelSize(dimenResId);
    }

    public static float pxToSp(Context context, int px) {
        return pxToSp(getDisplayMetrics(context), px);
    }

    public static float pxToSp(DisplayMetrics dm, int px) {
        return applyDimension(TypedValue.COMPLEX_UNIT_SP, px, dm);
    }

    public static float pxToDp(Context context, int px) {
        return pxToDp(getDisplayMetrics(context), px);
    }

    public static float pxToDp(DisplayMetrics dm, int px) {
        return applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, dm);
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    private static float applyDimension(int unit, float value, DisplayMetrics dm) {
        return TypedValue.applyDimension(unit, value, dm);
    }

    private ContextUtils() {
        //No instances
    }
}
