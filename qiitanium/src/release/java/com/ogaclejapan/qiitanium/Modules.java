package com.ogaclejapan.qiitanium;

import com.ogaclejapan.qiitanium.datasource.DataSourceModule;
import com.ogaclejapan.qiitanium.datasource.web.WebModule;
import com.ogaclejapan.qiitanium.domain.DomainModule;

public final class Modules {

  private Modules() {}

  public static AppModule appModule(Qiitanium app) {
    return new AppModule(app);
  }

  public static DomainModule domainModule() {
    return new DomainModule();
  }

  public static DataSourceModule dataSourceModule() {
    return new DataSourceModule();
  }

  public static WebModule webModule() {
    return new WebModule();
  }

}
