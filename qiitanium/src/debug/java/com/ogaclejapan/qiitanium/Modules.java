package com.ogaclejapan.qiitanium;

import com.ogaclejapan.qiitanium.datasource.DataSourceModule;
import com.ogaclejapan.qiitanium.datasource.DebugDataSourceModule;
import com.ogaclejapan.qiitanium.datasource.web.DebugWebModule;
import com.ogaclejapan.qiitanium.datasource.web.WebModule;
import com.ogaclejapan.qiitanium.domain.DomainModule;

public final class Modules {

  private Modules() {}

  public static AppModule appModule(Qiitanium app) {
    return new DebugAppModule(app);
  }

  public static DomainModule domainModule() {
    return new DomainModule();
  }

  public static DataSourceModule dataSourceModule() {
    return new DebugDataSourceModule();
  }

  public static WebModule webModule() {
    return new DebugWebModule();
  }

}
