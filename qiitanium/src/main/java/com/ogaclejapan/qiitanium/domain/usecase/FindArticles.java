package com.ogaclejapan.qiitanium.domain.usecase;

import com.ogaclejapan.qiitanium.domain.exception.DataAccessException;
import com.ogaclejapan.qiitanium.domain.model.Article;
import com.ogaclejapan.qiitanium.domain.model.Page;
import com.ogaclejapan.qiitanium.domain.repository.ArticleRepository;

import java.util.List;

import rx.functions.Func1;

public class FindArticles implements Func1<Page, List<Article>> {

  private final ArticleRepository repository;

  public FindArticles(final ArticleRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<Article> call(final Page page) throws DataAccessException {
    return repository.findAll(page);
  }

}
