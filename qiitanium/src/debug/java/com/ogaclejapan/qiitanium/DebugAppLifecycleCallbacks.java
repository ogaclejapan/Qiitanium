package com.ogaclejapan.qiitanium;

import com.facebook.stetho.Stetho;

import timber.log.Timber;

public class DebugAppLifecycleCallbacks extends AppLifecycleCallbacks {

  public DebugAppLifecycleCallbacks(Qiitanium app) {
    super(app);
  }

  @Override public void onCreate() {
    super.onCreate();
    setupStetho();
  }

  @Override public void onTerminate() {
    super.onTerminate();
  }

  @Override
  protected void setupLogger() {
    Timber.plant(new Timber.DebugTree());
  }

  protected void setupStetho() {
    Stetho.initialize(
        Stetho.newInitializerBuilder(app)
            .enableDumpapp(Stetho.defaultDumperPluginsProvider(app))
            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(app))
            .build());
  }

}
