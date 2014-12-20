package com.ogaclejapan.qiitanium.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.view.View;

public final class BitmapUtils {

    private static final float DEFAULT_BLUR_RADIUS = 12f;

    public static Bitmap blur(final View v) {
        return blur(v.getContext(), capture(v));
    }

    public static Bitmap blur(final View v, final float radius) {
        return blur(v.getContext(), capture(v), radius);
    }

    public static Bitmap blur(final Context context, final Bitmap image) {
        return blur(context, image, DEFAULT_BLUR_RADIUS);
    }

    public static Bitmap blur(final Context context, Bitmap image, final float radius) {
        Bitmap bitmap = image.copy(image.getConfig(), true);

        final RenderScript rs = RenderScript.create(context);
        final Allocation input = Allocation.createFromBitmap(rs, image,
                Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(radius);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(bitmap);
        return bitmap;
    }

    public static Bitmap capture(View v) {
        return capture(v, Bitmap.Config.ARGB_8888);
    }

    public static Bitmap capture(View v, Bitmap.Config config) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), config);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }

    private BitmapUtils() {
        //No instances
    }
}
