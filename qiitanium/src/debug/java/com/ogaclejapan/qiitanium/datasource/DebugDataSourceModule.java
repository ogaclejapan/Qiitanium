package com.ogaclejapan.qiitanium.datasource;

import android.app.Application;
import android.net.Uri;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

public class DebugDataSourceModule extends DataSourceModule {

  @Override
  public Picasso providePicasso(Application app, OkHttpClient client) {
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
