package com.ogaclejapan.qiitanium;

import timber.log.Timber;

public class DebugAppLifecycleCallbacks extends AppLifecycleCallbacks {

  public DebugAppLifecycleCallbacks(Qiitanium app) {
    super(app);
  }

  @Override
  protected void initializeLogger() {
    Timber.plant(new Timber.DebugTree());
  }

}
