package com.ogaclejapan.qiitanium.datasource;

import com.ogaclejapan.qiitanium.datasource.repository.ArticleDataRepository;
import com.ogaclejapan.qiitanium.datasource.repository.CommentDataRepository;
import com.ogaclejapan.qiitanium.datasource.repository.TagDataRepository;
import com.ogaclejapan.qiitanium.datasource.web.WebModule;
import com.ogaclejapan.qiitanium.datasource.web.api.QiitaApiV1;
import com.ogaclejapan.qiitanium.datasource.web.api.QiitaApiV2;
import com.ogaclejapan.qiitanium.domain.repository.ArticleRepository;
import com.ogaclejapan.qiitanium.domain.repository.CommentRepository;
import com.ogaclejapan.qiitanium.domain.repository.TagRepository;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.UrlConnectionDownloader;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = {WebModule.class},
        complete = false,
        library = true
)
public class DataSourceModule {

    @Provides
    @Singleton
    ArticleRepository provideArticleRepository(Application app, QiitaApiV1 api) {
        return new ArticleDataRepository(app, api);
    }

    @Provides
    @Singleton
    TagRepository provideTagRepository(Application app, QiitaApiV2 api) {
        return new TagDataRepository(app, api);
    }

    @Provides
    @Singleton
    CommentRepository provideCommentRepository(Application app, QiitaApiV2 api) {
        return new CommentDataRepository(app, api);
    }

    @Provides
    @Singleton
    Picasso providePicasso(Application app, OkHttpClient client) {
        return new Picasso.Builder(app)
                .downloader(new OkHttpDownloader(client))
                .build();
    }

}
