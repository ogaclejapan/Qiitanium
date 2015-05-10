package com.ogaclejapan.qiitanium;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class DebugAppModule extends AppModule {

  public DebugAppModule(Qiitanium app) {
    super(app);
  }

  @Override
  public Qiitanium.LifecycleCallbacks provideAppLifecycleCallbacks() {
    return new DebugAppLifecycleCallbacks(app);
  }

  @Override public RefWatcher provideRefWatcher() {
    return LeakCanary.install(app);
  }

}
