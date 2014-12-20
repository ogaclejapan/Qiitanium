package com.ogaclejapan.qiitanium.presentation.viewmodel;

import com.ogaclejapan.qiitanium.domain.model.Article;
import com.ogaclejapan.qiitanium.domain.model.Tag;
import com.ogaclejapan.rx.binding.RxProperty;
import com.ogaclejapan.rx.binding.RxReadOnlyProperty;
import com.ogaclejapan.rx.binding.RxUtils;

import rx.Observable;

public class ArticleViewModel extends AppViewModel {

    private final Article mArticle;
    private final RxProperty<String> mCreatedAt = RxProperty.create();
    private final RxReadOnlyProperty<String> mTag;

    protected ArticleViewModel(final Article article) {
        mArticle = article;
        mCreatedAt.bind(article.createdAt, TIMEAGO);
        mTag = RxReadOnlyProperty.of(article.tag());
    }

    public static ArticleViewModel create(final Article article) {
        return new ArticleViewModel(article);
    }

    public String id() {
        return mArticle.id;
    }

    public RxReadOnlyProperty<String> title() {
        return mArticle.title;
    }

    public RxReadOnlyProperty<String> excerpt() {
        return mArticle.excerpt;
    }

    public RxReadOnlyProperty<String> authorName() {
        return mArticle.author.name;
    }

    public RxReadOnlyProperty<String> authorThumbnailUrl() {
        return mArticle.author.thumbnailUrl;
    }

    public RxReadOnlyProperty<String> tag() {
        return mTag;
    }

    public RxReadOnlyProperty<Boolean> isBookmark() {
        return mArticle.isBookmark;
    }

    public RxReadOnlyProperty<String> createdAt() {
        return mCreatedAt;
    }

    public static class Mapper extends AppModelMapper<Article, ArticleViewModel> {

        @Override
        public ArticleViewModel call(final Article model) {
            return create(model);
        }

    }

}
