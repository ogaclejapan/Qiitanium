package com.ogaclejapan.qiitanium;

import com.crashlytics.android.Crashlytics;
import com.ogaclejapan.qiitanium.util.Objects;

import android.app.Application;
import android.content.Context;

import io.fabric.sdk.android.Fabric;
import javax.inject.Inject;

import dagger.ObjectGraph;
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

}
