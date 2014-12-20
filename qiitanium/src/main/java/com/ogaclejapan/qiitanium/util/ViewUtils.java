package com.ogaclejapan.qiitanium.util;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public final class ViewUtils {

    public static <T extends View> T findById(Activity activity, int id) {
        return Objects.<T>cast(activity.findViewById(id));
    }

    public static <T extends View> T findById(View view, int id) {
        return Objects.<T>cast(view.findViewById(id));
    }

    public static void setText(TextView v, int id, Object... formatArgs) {
        v.setText(ContextUtils.getString(v.getContext(), id, formatArgs));
    }

    public static void setHtmlText(TextView v, int id, Object... formatArgs) {
        v.setText(Html.fromHtml(ContextUtils.getString(v.getContext(), id, formatArgs)));
    }

    public static void setQuantityText(TextView v, int id, int quantity) {
        v.setText(ContextUtils.getQuantityString(v.getContext(), id, quantity));
    }

    public static void setQuantityHtmlText(TextView v, int id, int quantity) {
        v.setText(Html.fromHtml(ContextUtils.getQuantityString(v.getContext(), id, quantity)));
    }

    public static void setupWebView(WebView webview) {
        webview.setVerticalScrollbarOverlay(true);
        WebSettings settings = webview.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
    }

    public static boolean isVisible(View v) {
        return (v.getVisibility() == View.VISIBLE);
    }

    public static void visible(View v) {
        setVisibility(v, View.VISIBLE);
    }

    public static void invisible(View v) {
        setVisibility(v, View.INVISIBLE);
    }

    public static void gone(View v) {
        setVisibility(v, View.GONE);
    }

    private static void setVisibility(View v, int visibility) {
        if (v.getVisibility() != visibility) {
            v.setVisibility(visibility);
        }
    }

    private ViewUtils() {
        //No instances
    }
}
