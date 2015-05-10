package com.ogaclejapan.qiitanium.presentation.viewmodel;

import android.content.Context;
import android.text.TextUtils;

import com.ogaclejapan.qiitanium.domain.model.Article;
import com.ogaclejapan.qiitanium.domain.model.Articles;
import com.ogaclejapan.qiitanium.domain.model.Page;
import com.ogaclejapan.qiitanium.util.Objects;
import com.ogaclejapan.rx.binding.RxList;
import com.ogaclejapan.rx.binding.RxProperty;
import com.ogaclejapan.rx.binding.RxReadOnlyList;
import com.ogaclejapan.rx.binding.RxReadOnlyProperty;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import timber.log.Timber;

public class ArticleListViewModel extends AppViewModel {

  private final Action1<ArticleViewModel> setParentAction =
      new Action1<ArticleViewModel>() {
        @Override
        public void call(ArticleViewModel itemViewModel) {
          itemViewModel.setParent(ArticleListViewModel.this);
        }
      };

  private final Page page = Page.create();
  private final Articles articles;
  private final RxProperty<Boolean> isLoading;
  private final RxProperty<Boolean> isRefreshing;
  private final RxList<ArticleViewModel> items;
  private final ArticleViewModel.Mapper itemMapper;
  private String tagId = null;

  protected ArticleListViewModel(final Articles articles) {
    this.articles = articles;
    isRefreshing = RxProperty.of(false);
    isLoading = RxProperty.of(false);
    isLoading.bind(isRefreshing);
    items = RxList.create();
    itemMapper = new ArticleViewModel.Mapper();
  }

  public static ArticleListViewModel create(final Context context) {
    return new ArticleListViewModel(Articles.with(context));
  }

  public void setTagId(String tagId) {
    this.tagId = tagId;
  }

  public RxReadOnlyProperty<Boolean> isLoading() {
    return isLoading;
  }

  public RxReadOnlyProperty<Boolean> isRefreshing() {
    return isRefreshing;
  }

  public RxReadOnlyList<ArticleViewModel> items() {
    return items;
  }

  public void refresh() {
    isRefreshing.set(true);
    page.reset();
    load().subscribe(observerListArticle());
  }

  public void loadMore() {
    if (page.isLast()) {
      return;
    }
    isLoading.set(true);
    load().subscribe(observerListArticle());
  }

  private Observable<List<Article>> load() {
    return (TextUtils.isEmpty(tagId))
        ? articles.loadAll(page)
        : articles.loadAllByTag(tagId, page);
  }

  private Observer<List<Article>> observerListArticle() {
    return new Observer<List<Article>>() {
      @Override
      public void onCompleted() {
        isRefreshing.set(false);
      }

      @Override
      public void onError(final Throwable e) {
        isRefreshing.set(false);
        Timber.e(e, "Failed to load article list.");
      }

      @Override
      public void onNext(final List<Article> articles) {
        if (isRefreshing.get()) {
          items.clear();
        }
        items.addAll(Objects.map(articles, itemMapper, setParentAction));
      }
    };
  }

}
