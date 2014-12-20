package com.ogaclejapan.qiitanium;

import com.ogaclejapan.qiitanium.datasource.DebugDataSourceModule;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

@Module(
        addsTo = QiitaniumModule.class,
        includes = {
                DebugDataSourceModule.class
        },
        overrides = true
)
public class DebugQiitaniumModule {

    @Provides
    Timber.Tree provideTree() {
        return new Timber.DebugTree();
    }

}
