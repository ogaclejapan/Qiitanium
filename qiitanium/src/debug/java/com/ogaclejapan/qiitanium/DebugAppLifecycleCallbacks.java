package com.ogaclejapan.qiitanium;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.timber.StethoTree;

import timber.log.Timber;

public class DebugAppLifecycleCallbacks extends AppLifecycleCallbacks {

  public DebugAppLifecycleCallbacks(Qiitanium app) {
    super(app);
  }

  @Override
  public void onCreate() {
    setupStetho();
    super.onCreate();
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
  }

  @Override
  protected void setupLogger() {
    Timber.plant(new Timber.DebugTree());
    Timber.plant(new StethoTree());
  }

  protected void setupStetho() {
    Stetho.initialize(
        Stetho.newInitializerBuilder(app)
            .enableDumpapp(Stetho.defaultDumperPluginsProvider(app))
            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(app))
            .build());
  }

}
