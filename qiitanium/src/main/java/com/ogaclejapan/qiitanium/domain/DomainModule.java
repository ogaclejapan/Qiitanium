package com.ogaclejapan.qiitanium.domain;

import com.ogaclejapan.qiitanium.domain.repository.ArticleRepository;
import com.ogaclejapan.qiitanium.domain.repository.CommentRepository;
import com.ogaclejapan.qiitanium.domain.repository.TagRepository;
import com.ogaclejapan.qiitanium.domain.usecase.FindArticleById;
import com.ogaclejapan.qiitanium.domain.usecase.FindArticles;
import com.ogaclejapan.qiitanium.domain.usecase.FindArticlesByTag;
import com.ogaclejapan.qiitanium.domain.usecase.FindCommentsInArticle;
import com.ogaclejapan.qiitanium.domain.usecase.FindTags;

import dagger.Module;
import dagger.Provides;

@Module
public class DomainModule {

  @Provides FindArticleById provideFindArticleById(ArticleRepository repository) {
    return new FindArticleById(repository);
  }

  @Provides FindArticles provideFindArticle(ArticleRepository repository) {
    return new FindArticles(repository);
  }

  @Provides FindArticlesByTag provideFindArticlesByTag(ArticleRepository repository) {
    return new FindArticlesByTag(repository);
  }

  @Provides FindCommentsInArticle provideFindCommentsInArticle(CommentRepository repository) {
    return new FindCommentsInArticle(repository);
  }

  @Provides FindTags provideFindTags(TagRepository repository) {
    return new FindTags(repository);
  }

}
