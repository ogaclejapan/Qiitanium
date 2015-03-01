package com.ogaclejapan.qiitanium;

import com.crashlytics.android.Crashlytics;
import com.norbsoft.typefacehelper.TypefaceCollection;
import com.norbsoft.typefacehelper.TypefaceHelper;
import com.ogaclejapan.qiitanium.util.Objects;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.StringRes;

import javax.inject.Inject;

import dagger.ObjectGraph;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class Qiitanium extends Application {

    private ObjectGraph mObjectGraph;

    @Inject
    ActivityLifecycleCallbacks mActivityLifecycleCallbacks;

    @Inject
    Timber.Tree mTree;

    public static Qiitanium from(Context context) {
        return Objects.cast(context.getApplicationContext());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        initObjectGraph();
        initLogger();
        initTypeFace();
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }

    @Override
    public void onTerminate() {
        unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
        super.onTerminate();
    }

    public <T> T inject(T instance) {
        mObjectGraph.inject(instance);
        return instance;
    }

    public ObjectGraph graph() {
        return mObjectGraph;
    }

    protected void initObjectGraph() {
        mObjectGraph = ObjectGraph.create(Modules.list(this));
        mObjectGraph.inject(this);
    }

    protected void initLogger() {
        if (mTree != null) {
            Timber.plant(mTree);
        }
    }

    protected void initTypeFace() {
        TypefaceHelper.init(new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, createTypefaceFromAsset(R.string.font_ubuntu_regular))
                .set(Typeface.BOLD, createTypefaceFromAsset(R.string.font_ubuntu_bold))
                .set(Typeface.ITALIC, createTypefaceFromAsset(R.string.font_ubuntu_regular_italic))
                .set(Typeface.BOLD_ITALIC, createTypefaceFromAsset(R.string.font_ubuntu_bold_italic))
                .create());
    }

    private Typeface createTypefaceFromAsset(@StringRes int resId) {
        return Typeface.createFromAsset(getAssets(), getString(resId));
    }

}
