package com.ogaclejapan.qiitanium.datasource.web.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.ogaclejapan.qiitanium.datasource.web.dto.ArticleDto;
import com.ogaclejapan.qiitanium.datasource.web.dto.ResponseDto;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

public class QiitaApiV1 extends QiitaBaseApi {

    private static final String PATH_BASE = "/v1";
    private static final String PATH_ITEMS = PATH_BASE + "/items";
    private static final String PATH_ITEM = PATH_ITEMS + "/%s";
    private static final String PATH_TAGS = PATH_BASE + "/tags";
    private static final String PATH_TAGS_ITEMS = PATH_TAGS + "/%s/items";

    private final TypeToken<List<ArticleDto>> LIST_TYPEOF_ARTICLE =
            new TypeToken<List<ArticleDto>>() {};

    @Inject
    public QiitaApiV1(OkHttpClient okHttp, Gson gson) {
        super(okHttp, gson);
    }

    public ResponseDto<ArticleDto> getArticleById(String uuid) {
        return execute(get(url(PATH_ITEM, uuid)), ArticleDto.class);
    }

    public ResponseDto<List<ArticleDto>> getLatestArticles(int page) {
        return execute(get(url(page, PATH_ITEMS)), LIST_TYPEOF_ARTICLE);
    }

    public ResponseDto<List<ArticleDto>> getArticlesByTag(String name, int page) {
        return execute(get(url(page, PATH_TAGS_ITEMS, name)), LIST_TYPEOF_ARTICLE);
    }

}
