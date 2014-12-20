package com.ogaclejapan.qiitanium.util;

import android.os.Looper;

public class SystemUtils {

    public static boolean isMainThread() {
        return (Thread.currentThread() == Looper.getMainLooper().getThread());
    }

    private SystemUtils() {
        //No instances.
    }
}
