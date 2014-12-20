package com.ogaclejapan.qiitanium;


import com.ogaclejapan.qiitanium.datasource.DataSourceModule;
import com.ogaclejapan.qiitanium.domain.DomainModule;
import com.ogaclejapan.qiitanium.presentation.PresentationModule;
import com.ogaclejapan.qiitanium.util.CrashlyticsTree;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

@Module(
        includes = {
                PresentationModule.class,
                DomainModule.class,
                DataSourceModule.class
        },
        injects = {
                Qiitanium.class
        }
)
public class QiitaniumModule {

    private final Qiitanium app;

    public QiitaniumModule(Qiitanium app) {
        this.app = app;
    }

    @Singleton
    @Provides
    Application provideApplication() {
        return app;
    }

    @Provides
    Timber.Tree provideTree() {
        return new CrashlyticsTree();
    }

}
