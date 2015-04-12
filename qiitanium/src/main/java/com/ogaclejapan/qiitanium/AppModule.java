package com.ogaclejapan.qiitanium;


import android.app.Application;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    protected final Qiitanium mApp;

    public AppModule(Qiitanium app) {
        this.mApp = app;
    }

    @Provides
    public Application provideApplication() {
        return mApp;
    }

    @Provides
    public Application.ActivityLifecycleCallbacks provideActivityLifecycleCallbacks() {
        return new AppActivityLifecycleCallbacks();
    }

    @Provides
    public Qiitanium.LifecycleCallbacks provideAppLifecycleCallbacks() {
        return new AppLifecycleCallbacks(mApp);
    }

}
