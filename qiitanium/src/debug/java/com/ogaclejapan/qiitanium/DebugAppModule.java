package com.ogaclejapan.qiitanium;

public class DebugAppModule extends AppModule {

    public DebugAppModule(Qiitanium app) {
        super(app);
    }

    @Override
    public Qiitanium.LifecycleCallbacks provideAppLifecycleCallbacks() {
        return new DebugAppLifecycleCallbacks(mApp);
    }

}
