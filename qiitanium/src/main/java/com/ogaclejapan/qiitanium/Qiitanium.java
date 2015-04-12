package com.ogaclejapan.qiitanium;

import com.ogaclejapan.qiitanium.util.Objects;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;

public class Qiitanium extends Application {

    public static interface LifecycleCallbacks {

        /**
         * Call when the {@link Qiitanium#onCreate()}.
         */
        void onCreate();

        /**
         * Call when the {@link Qiitanium#onTerminate()}.
         */
        void onTerminate();

    }

    @Inject
    LifecycleCallbacks mAppLifecycleCallbacks;

    @Inject
    ActivityLifecycleCallbacks mActivityLifecycleCallbacks;

    private AppComponent mComponent;

    public static Qiitanium from(Context context) {
        return Objects.cast(context.getApplicationContext());
    }

    public static AppComponent appComponent(Context context) {
        return from(context).getComponent();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setupComponent();
        mAppLifecycleCallbacks.onCreate();
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }

    @Override
    public void onTerminate() {
        unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
        mAppLifecycleCallbacks.onTerminate();
        super.onTerminate();
    }

    /**
     * @return the {@link com.ogaclejapan.qiitanium.AppComponent}
     */
    public AppComponent getComponent() {
        return mComponent;
    }

    /**
     * Initialize the {@link com.ogaclejapan.qiitanium.AppComponent} of Dagger.
     */
    protected void setupComponent() {
        mComponent = DaggerAppComponent.builder()
                .appModule(Modules.appModule(this))
                .domainModule(Modules.domainModule())
                .dataSourceModule(Modules.dataSourceModule())
                .webModule(Modules.webModule())
                .build();

        mComponent.inject(this);
    }

}
