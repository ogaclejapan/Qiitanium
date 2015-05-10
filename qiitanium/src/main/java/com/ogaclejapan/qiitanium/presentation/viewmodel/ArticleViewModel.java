package com.ogaclejapan.qiitanium.presentation.viewmodel;

import com.ogaclejapan.qiitanium.domain.model.Article;
import com.ogaclejapan.rx.binding.RxProperty;
import com.ogaclejapan.rx.binding.RxReadOnlyProperty;

public class ArticleViewModel extends AppViewModel {

  private final Article article;
  private final RxProperty<String> createdAt = RxProperty.create();
  private final RxReadOnlyProperty<String> tag;

  protected ArticleViewModel(final Article article) {
    this.article = article;
    this.createdAt.bind(article.createdAt, TIMEAGO);
    this.tag = RxReadOnlyProperty.of(article.tag());
  }

  public static ArticleViewModel create(final Article article) {
    return new ArticleViewModel(article);
  }

  public String id() {
    return article.id;
  }

  public RxReadOnlyProperty<String> title() {
    return article.title;
  }

  public RxReadOnlyProperty<String> excerpt() {
    return article.excerpt;
  }

  public RxReadOnlyProperty<String> authorName() {
    return article.author.name;
  }

  public RxReadOnlyProperty<String> authorThumbnailUrl() {
    return article.author.thumbnailUrl;
  }

  public RxReadOnlyProperty<String> tag() {
    return tag;
  }

  public RxReadOnlyProperty<Boolean> isBookmark() {
    return article.isBookmark;
  }

  public RxReadOnlyProperty<String> createdAt() {
    return createdAt;
  }

  public static class Mapper extends AppModelMapper<Article, ArticleViewModel> {

    @Override
    public ArticleViewModel call(final Article model) {
      return create(model);
    }

  }

}
