package com.ogaclejapan.qiitanium.datasource;

import android.app.Application;

import com.ogaclejapan.qiitanium.datasource.repository.ArticleDataRepository;
import com.ogaclejapan.qiitanium.datasource.repository.CommentDataRepository;
import com.ogaclejapan.qiitanium.datasource.repository.TagDataRepository;
import com.ogaclejapan.qiitanium.datasource.web.api.QiitaApiV1;
import com.ogaclejapan.qiitanium.datasource.web.api.QiitaApiV2;
import com.ogaclejapan.qiitanium.domain.repository.ArticleRepository;
import com.ogaclejapan.qiitanium.domain.repository.CommentRepository;
import com.ogaclejapan.qiitanium.domain.repository.TagRepository;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

@Module
public class DataSourceModule {

  @Provides
  public ArticleRepository provideArticleRepository(Application app, QiitaApiV1 api) {
    return new ArticleDataRepository(app, api);
  }

  @Provides
  public TagRepository provideTagRepository(Application app, QiitaApiV2 api) {
    return new TagDataRepository(app, api);
  }

  @Provides
  public CommentRepository provideCommentRepository(Application app, QiitaApiV2 api) {
    return new CommentDataRepository(app, api);
  }

  @Provides
  public Picasso providePicasso(Application app, OkHttpClient client) {
    return new Picasso.Builder(app)
        .downloader(new OkHttpDownloader(client))
        .build();
  }

}
