package com.ogaclejapan.qiitanium;

import com.ogaclejapan.qiitanium.datasource.DataSourceComponent;
import com.ogaclejapan.qiitanium.datasource.DataSourceModule;
import com.ogaclejapan.qiitanium.datasource.web.WebComponent;
import com.ogaclejapan.qiitanium.datasource.web.WebModule;
import com.ogaclejapan.qiitanium.domain.DomainComponent;
import com.ogaclejapan.qiitanium.domain.DomainModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                AppModule.class,
                DomainModule.class,
                DataSourceModule.class,
                WebModule.class
        })
public interface AppComponent extends DomainComponent, DataSourceComponent, WebComponent {

    void inject(Qiitanium app);


}
