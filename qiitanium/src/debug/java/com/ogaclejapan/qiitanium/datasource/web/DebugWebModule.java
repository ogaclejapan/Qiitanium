package com.ogaclejapan.qiitanium.datasource.web;

import android.app.Application;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;

public class DebugWebModule extends WebModule {

  @Override public OkHttpClient provideOkHttpClient(Application app) {
    OkHttpClient client = super.provideOkHttpClient(app);
    client.networkInterceptors().add(new StethoInterceptor());
    return client;
  }

}
