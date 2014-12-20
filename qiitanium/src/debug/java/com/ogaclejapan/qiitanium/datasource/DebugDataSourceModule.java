package com.ogaclejapan.qiitanium.datasource;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.UrlConnectionDownloader;

import android.app.Application;
import android.net.Uri;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

@Module(complete = false, library = true, overrides = true)
public class DebugDataSourceModule {

    @Provides
    @Singleton
    Picasso providePicasso(Application app, OkHttpClient client) {
        return new Picasso.Builder(app)
                .indicatorsEnabled(false)
                .downloader(new OkHttpDownloader(client))
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception e) {
                        Timber.w(e, "Failed to load image: %s", uri);
                    }
                })
                .build();
    }


}
