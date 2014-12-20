package com.ogaclejapan.qiitanium.datasource.web;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.datasource.web.api.QiitaApiV1;
import com.ogaclejapan.qiitanium.datasource.web.api.QiitaApiV2;
import com.ogaclejapan.qiitanium.util.ContextUtils;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import android.app.Application;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

@Module(
        injects = {QiitaApiV1.class, QiitaApiV2.class},
        complete = false,
        library = true
)
public class WebModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Application app) {
        final int size = ContextUtils.getInteger(app, R.integer.http_disk_cache_size);

        final OkHttpClient client = new OkHttpClient();
        try {
            File cacheDir = new File(app.getCacheDir(), "http");
            Cache cache = new Cache(cacheDir, size);
            client.setCache(cache);
        } catch (IOException e) {
            Timber.e(e, "Unable to install disk cache.");
        }

        return client;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

}
