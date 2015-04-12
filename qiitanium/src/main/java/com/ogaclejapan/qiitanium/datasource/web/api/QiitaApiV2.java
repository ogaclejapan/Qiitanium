package com.ogaclejapan.qiitanium.datasource.web.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.ogaclejapan.qiitanium.datasource.web.dto.CommentDto;
import com.ogaclejapan.qiitanium.datasource.web.dto.ErrorDto;
import com.ogaclejapan.qiitanium.datasource.web.dto.PageDto;
import com.ogaclejapan.qiitanium.datasource.web.dto.ResponseDto;
import com.ogaclejapan.qiitanium.datasource.web.dto.TagDto;
import com.ogaclejapan.qiitanium.domain.exception.DataAccessException;
import com.ogaclejapan.qiitanium.util.IOUtils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import android.app.Application;
import android.content.Context;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

public class QiitaApiV2 extends QiitaBaseApi {

    private static final String PATH_BASE = "/v2";
    private static final String PATH_ITEMS = PATH_BASE + "/items";
    private static final String PATH_TAGS = PATH_BASE + "/tags";
    private static final String PATH_COMMENTS = PATH_ITEMS + "/%s/comments";

    private final TypeToken<List<CommentDto>> LIST_TYPEOF_COMMENT =
            new TypeToken<List<CommentDto>>() {};

    private final TypeToken<List<TagDto>> LIST_TYPEOF_TAG =
            new TypeToken<List<TagDto>>() {};

    private final Context mApplicationContext;

    @Inject
    public QiitaApiV2(OkHttpClient okHttp, Gson gson, Application app) {
        super(okHttp, gson);
        mApplicationContext = app;
    }

    public ResponseDto<List<CommentDto>> getComments(String articleId, int page) {
        return execute(get(url(page, PATH_COMMENTS, articleId)), LIST_TYPEOF_COMMENT);
    }

//    public ResponseDto<List<TagDto>> getTags(final int page) {
//        return execute(get(url(page, PATH_TAGS)), LIST_TYPEOF_TAG);
//    }

    /**
     * 人気のタグ順で取得できないようなので、
     * qiitaタグ一覧ページから事前に取得したおすすめタグ(assets/tags.json)のみ返す
     */
    public ResponseDto<List<TagDto>> getTags(int page) {
        try {
            String json = IOUtils.readAllFromAssets(mApplicationContext, "tags.json");
            ResponseDto<List<TagDto>> responseDto = new ResponseDto<>();
            responseDto.body = objectify(json, LIST_TYPEOF_TAG);
            responseDto.page = new PageDto();
            return responseDto;
        } catch (IOException ioe) {
            throw new DataAccessException(ioe);
        }
    }


    @Override
    protected String fetchErrorMessage(Response response) {
        switch (response.code()) {
            case 400:
            case 403:
            case 404:
            case 500:
                try {
                    return objectify(response, ErrorDto.class).message;
                } catch (IOException ioe) {
                    Timber.e(ioe, "Failed to read response body.");
                }
            default:
                //Do nothing.
        }

        String message = "unknown";
        if (response.body() != null) {
            try {
                return response.body().string();
            } catch (IOException ioe) {
                Timber.e(ioe, "Failed to read response body.");
            }
        }

        return message;
    }

}
