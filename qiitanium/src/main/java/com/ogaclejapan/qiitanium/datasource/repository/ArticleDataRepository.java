package com.ogaclejapan.qiitanium.datasource.repository;

import com.ogaclejapan.qiitanium.datasource.web.api.QiitaApiV1;
import com.ogaclejapan.qiitanium.datasource.web.dto.ArticleDto;
import com.ogaclejapan.qiitanium.datasource.web.dto.ResponseDto;
import com.ogaclejapan.qiitanium.datasource.web.dto.mapper.DtoToEntity;
import com.ogaclejapan.qiitanium.domain.exception.DataAccessException;
import com.ogaclejapan.qiitanium.domain.model.Article;
import com.ogaclejapan.qiitanium.domain.model.Page;
import com.ogaclejapan.qiitanium.domain.repository.ArticleRepository;

import android.content.Context;

import java.util.List;

public class ArticleDataRepository implements ArticleRepository {

    private final QiitaApiV1 mApi;
    private final DtoToEntity.ArticleMapper mMapper;

    public ArticleDataRepository(Context context, QiitaApiV1 api) {
        mApi = api;
        mMapper = new DtoToEntity.ArticleMapper(context);
    }

    @Override
    public Article findById(String articleId) throws DataAccessException {
        final ResponseDto<ArticleDto> response = mApi.getArticleById(articleId);
        return mMapper.map(response.body);
    }

    @Override
    public List<Article> findAll(Page page) {
        final int pageNum = page.getAndIncrement();
        final ResponseDto<List<ArticleDto>> response = mApi.getLatestArticles(pageNum);
        page.setLast(response.page.last);
        return mMapper.map(response.body);
    }

    @Override
    public List<Article> findAllByTag(String tagId, Page page) {
        final int pageNum = page.getAndIncrement();
        final ResponseDto<List<ArticleDto>> response = mApi.getArticlesByTag(tagId, pageNum);
        page.setLast(response.page.last);
        return mMapper.map(response.body);
    }

}
