package com.ogaclejapan.qiitanium.datasource.web;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.datasource.web.api.QiitaApiV1;
import com.ogaclejapan.qiitanium.datasource.web.api.QiitaApiV2;
import com.ogaclejapan.qiitanium.util.ResUtils;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import android.app.Application;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class WebModule {

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient(Application app) {
        final int size = ResUtils.getInteger(app, R.integer.http_disk_cache_size);

        final OkHttpClient client = new OkHttpClient();
        File cacheDir = new File(app.getCacheDir(), "http");
        Cache cache = new Cache(cacheDir, size);
        client.setCache(cache);

        return client;
    }

    @Singleton
    @Provides
    public Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

}
