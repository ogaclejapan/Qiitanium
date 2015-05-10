package com.ogaclejapan.qiitanium;

import android.app.Application;
import android.content.Context;

import com.ogaclejapan.qiitanium.util.Objects;

import javax.inject.Inject;

public class Qiitanium extends Application {

  @Inject LifecycleCallbacks appLifecycleCallbacks;
  @Inject ActivityLifecycleCallbacks activityLifecycleCallbacks;

  private AppComponent appComponent;

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
    appLifecycleCallbacks.onCreate();
    registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
  }

  @Override
  public void onTerminate() {
    unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
    appLifecycleCallbacks.onTerminate();
    super.onTerminate();
  }

  /**
   * @return the {@link com.ogaclejapan.qiitanium.AppComponent}
   */
  public AppComponent getComponent() {
    return appComponent;
  }

  /**
   * Initialize the {@link com.ogaclejapan.qiitanium.AppComponent} of Dagger.
   */
  protected void setupComponent() {
    appComponent = DaggerAppComponent.builder()
        .appModule(Modules.appModule(this))
        .domainModule(Modules.domainModule())
        .dataSourceModule(Modules.dataSourceModule())
        .webModule(Modules.webModule())
        .build();

    appComponent.inject(this);
  }

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

}
