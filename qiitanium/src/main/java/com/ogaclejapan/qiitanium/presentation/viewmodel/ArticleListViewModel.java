package com.ogaclejapan.qiitanium.presentation.viewmodel;

import com.ogaclejapan.qiitanium.domain.model.Article;
import com.ogaclejapan.qiitanium.domain.model.Articles;
import com.ogaclejapan.qiitanium.domain.model.Page;
import com.ogaclejapan.qiitanium.util.Objects;
import com.ogaclejapan.rx.binding.RxList;
import com.ogaclejapan.rx.binding.RxProperty;
import com.ogaclejapan.rx.binding.RxReadOnlyList;
import com.ogaclejapan.rx.binding.RxReadOnlyProperty;

import android.content.Context;
import android.text.TextUtils;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import timber.log.Timber;

public class ArticleListViewModel extends AppViewModel {

    private final Action1<ArticleViewModel> mSetParentAction =
            new Action1<ArticleViewModel>() {
                @Override
                public void call(ArticleViewModel itemViewModel) {
                    itemViewModel.setParent(ArticleListViewModel.this);
                }
            };

    private final Page mPage = Page.create();
    private final Articles mArticles;

    private final RxProperty<Boolean> mIsLoading;
    private final RxProperty<Boolean> mIsRefreshing;
    private final RxList<ArticleViewModel> mItems;
    private final ArticleViewModel.Mapper mItemMapper;

    private String mTagId = null;

    protected ArticleListViewModel(final Articles articles) {
        mArticles = articles;
        mIsRefreshing = RxProperty.of(false);
        mIsLoading = RxProperty.of(false);
        mIsLoading.bind(mIsRefreshing);
        mItems = RxList.create();
        mItemMapper = new ArticleViewModel.Mapper();
    }

    public static ArticleListViewModel create(final Context context) {
        return new ArticleListViewModel(Articles.with(context));
    }

    public void setTagId(String tagId) {
        mTagId = tagId;
    }

    public RxReadOnlyProperty<Boolean> isLoading() {
        return mIsLoading;
    }

    public RxReadOnlyProperty<Boolean> isRefreshing() {
        return mIsRefreshing;
    }

    public RxReadOnlyList<ArticleViewModel> items() {
        return mItems;
    }

    public void refresh() {
        mIsRefreshing.set(true);
        mPage.reset();
        load().subscribe(observerListArticle());
    }

    public void loadMore() {
        if (mPage.isLast()) {
            return;
        }
        mIsLoading.set(true);
        load().subscribe(observerListArticle());
    }

    private Observable<List<Article>> load() {
        return (TextUtils.isEmpty(mTagId))
                ? mArticles.loadAll(mPage)
                : mArticles.loadAllByTag(mTagId, mPage);
    }

    private Observer<List<Article>> observerListArticle() {
        return new Observer<List<Article>>() {
            @Override
            public void onCompleted() {
                mIsRefreshing.set(false);
            }

            @Override
            public void onError(final Throwable e) {
                mIsRefreshing.set(false);
                Timber.e(e, "Failed to load article list.");
            }

            @Override
            public void onNext(final List<Article> articles) {
                if (mIsRefreshing.get()) {
                    mItems.clear();
                }
                mItems.addAll(Objects.map(articles, mItemMapper, mSetParentAction));
            }
        };
    }

}
