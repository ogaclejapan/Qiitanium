package com.ogaclejapan.qiitanium;

import com.crashlytics.android.Crashlytics;
import com.norbsoft.typefacehelper.TypefaceCollection;
import com.norbsoft.typefacehelper.TypefaceHelper;
import com.ogaclejapan.qiitanium.util.CrashlyticsTree;

import android.graphics.Typeface;
import android.support.annotation.StringRes;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class AppLifecycleCallbacks implements Qiitanium.LifecycleCallbacks {

    protected final Qiitanium mApp;

    public AppLifecycleCallbacks(Qiitanium app) {
        mApp = app;
    }

    @Override
    public void onCreate() {
        initializeFabric();
        initializeLogger();
        initializeTypeFace();
    }

    @Override
    public void onTerminate() {
        // Do nothing.
    }

    protected void initializeFabric() {
        Fabric.with(mApp, new Crashlytics());
    }

    protected void initializeLogger() {
        Timber.plant(new CrashlyticsTree());
    }

    protected void initializeTypeFace() {
        TypefaceHelper.init(new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, createTypefaceFromAsset(R.string.font_ubuntu_regular))
                .set(Typeface.BOLD, createTypefaceFromAsset(R.string.font_ubuntu_bold))
                .set(Typeface.ITALIC, createTypefaceFromAsset(R.string.font_ubuntu_regular_italic))
                .set(Typeface.BOLD_ITALIC,
                        createTypefaceFromAsset(R.string.font_ubuntu_bold_italic))
                .create());
    }


    protected Typeface createTypefaceFromAsset(@StringRes int resId) {
        return Typeface.createFromAsset(mApp.getAssets(), mApp.getString(resId));
    }

}
