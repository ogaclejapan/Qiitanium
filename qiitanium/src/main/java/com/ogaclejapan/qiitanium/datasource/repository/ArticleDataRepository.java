package com.ogaclejapan.qiitanium.datasource.repository;

import android.content.Context;

import com.ogaclejapan.qiitanium.datasource.web.api.QiitaApiV1;
import com.ogaclejapan.qiitanium.datasource.web.dto.ArticleDto;
import com.ogaclejapan.qiitanium.datasource.web.dto.ResponseDto;
import com.ogaclejapan.qiitanium.datasource.web.dto.mapper.DtoToEntity;
import com.ogaclejapan.qiitanium.domain.exception.DataAccessException;
import com.ogaclejapan.qiitanium.domain.model.Article;
import com.ogaclejapan.qiitanium.domain.model.Page;
import com.ogaclejapan.qiitanium.domain.repository.ArticleRepository;

import java.util.List;

public class ArticleDataRepository implements ArticleRepository {

  private final QiitaApiV1 api;
  private final DtoToEntity.ArticleMapper mapper;

  public ArticleDataRepository(Context context, QiitaApiV1 api) {
    this.api = api;
    this.mapper = new DtoToEntity.ArticleMapper(context);
  }

  @Override
  public Article findById(String articleId) throws DataAccessException {
    final ResponseDto<ArticleDto> response = api.getArticleById(articleId);
    return mapper.map(response.body);
  }

  @Override
  public List<Article> findAll(Page page) {
    final int pageNum = page.getAndIncrement();
    final ResponseDto<List<ArticleDto>> response = api.getLatestArticles(pageNum);
    page.setLast(response.page.last);
    return mapper.map(response.body);
  }

  @Override
  public List<Article> findAllByTag(String tagId, Page page) {
    final int pageNum = page.getAndIncrement();
    final ResponseDto<List<ArticleDto>> response = api.getArticlesByTag(tagId, pageNum);
    page.setLast(response.page.last);
    return mapper.map(response.body);
  }

}
