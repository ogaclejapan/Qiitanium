package com.ogaclejapan.qiitanium.domain.usecase;

import com.ogaclejapan.qiitanium.domain.model.Article;
import com.ogaclejapan.qiitanium.domain.repository.ArticleRepository;

import rx.functions.Func1;

public class FindArticleById implements Func1<String, Article> {

  private final ArticleRepository repository;

  public FindArticleById(ArticleRepository repository) {
    this.repository = repository;
  }

  @Override
  public Article call(final String articleId) {
    return repository.findById(articleId);
  }

}
