package com.ogaclejapan.qiitanium;

import android.app.Application;

import com.squareup.leakcanary.RefWatcher;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

  protected final Qiitanium app;

  public AppModule(Qiitanium app) {
    this.app = app;
  }

  @Singleton
  @Provides
  public Application provideApplication() {
    return app;
  }

  @Singleton
  @Provides
  public Application.ActivityLifecycleCallbacks provideActivityLifecycleCallbacks() {
    return new AppActivityLifecycleCallbacks();
  }

  @Singleton
  @Provides
  public Qiitanium.LifecycleCallbacks provideAppLifecycleCallbacks() {
    return new AppLifecycleCallbacks(app);
  }

  @Singleton
  @Provides
  public RefWatcher provideRefWatcher() {
    return RefWatcher.DISABLED;
  }

}
