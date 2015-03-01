package com.ogaclejapan.qiitanium.util;

import android.os.Looper;

public class SysUtils {

    public static boolean isMainThread() {
        return (Thread.currentThread() == Looper.getMainLooper().getThread());
    }

    private SysUtils() {
        //No instances.
    }
}
