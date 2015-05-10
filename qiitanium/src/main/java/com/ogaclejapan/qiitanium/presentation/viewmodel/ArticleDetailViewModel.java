package com.ogaclejapan.qiitanium.presentation.viewmodel;

import android.content.Context;

import com.ogaclejapan.qiitanium.domain.model.Article;
import com.ogaclejapan.qiitanium.domain.model.Articles;
import com.ogaclejapan.qiitanium.util.Objects;
import com.ogaclejapan.rx.binding.Rx;
import com.ogaclejapan.rx.binding.RxList;
import com.ogaclejapan.rx.binding.RxProperty;
import com.ogaclejapan.rx.binding.RxReadOnlyList;
import com.ogaclejapan.rx.binding.RxReadOnlyProperty;

import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.Subscriptions;

public class ArticleDetailViewModel extends AppViewModel {

  private final Articles articles;
  private final TagViewModel.Mapper tagMapper;
  private final RxProperty<String> title = RxProperty.create();
  private final RxProperty<String> authorName = RxProperty.create();
  private final RxProperty<String> authorThumbnailUrl = RxProperty.create();
  private final RxProperty<String> authorUrl = RxProperty.create();
  private final RxProperty<String> createdAt = RxProperty.create();
  private final RxProperty<String> contentHtml = RxProperty.create();
  private final RxProperty<String> contentUrl = RxProperty.create();
  private final RxList<TagViewModel> tags = RxList.create();

  protected ArticleDetailViewModel(Articles articles) {
    this.articles = articles;
    this.tagMapper = new TagViewModel.Mapper();
  }

  public static ArticleDetailViewModel create(Context context) {
    return new ArticleDetailViewModel(Articles.with(context));
  }

  public RxReadOnlyProperty<String> title() {
    return title;
  }

  public RxReadOnlyProperty<String> authorName() {
    return authorName;
  }

  public RxReadOnlyProperty<String> authorThumbnailUrl() {
    return authorThumbnailUrl;
  }

  public RxReadOnlyProperty<String> authorUrl() {
    return authorUrl;
  }

  public RxReadOnlyProperty<String> createdAt() {
    return createdAt;
  }

  public RxReadOnlyProperty<String> contentHtml() {
    return contentHtml;
  }

  public RxReadOnlyList<TagViewModel> tags() {
    return tags;
  }

  public RxReadOnlyProperty<String> contentUrl() {
    return contentUrl;
  }

  public void loadArticle(String id) {
    articles.loadById(id).subscribe(new Action1<Article>() {
      @Override
      public void call(final Article article) {

        Subscription s = Subscriptions.from(
            title.bind(article.title),
            authorName.bind(article.author.name),
            authorThumbnailUrl.bind(article.author.thumbnailUrl),
            createdAt.bind(article.createdAt, TIMEAGO),
            contentHtml.bind(article.html())
        );

        add(s);

        authorUrl.set(article.author.url());
        contentUrl.set(article.url());
        tags.addAll(Objects.map(article.tags, tagMapper));

      }
    }, Rx.ERROR_ACTION_EMPTY);
  }

}
