package com.ogaclejapan.qiitanium.presentation.viewmodel;

import com.ogaclejapan.qiitanium.domain.model.Article;
import com.ogaclejapan.qiitanium.domain.model.Articles;
import com.ogaclejapan.qiitanium.util.Objects;
import com.ogaclejapan.rx.binding.Rx;
import com.ogaclejapan.rx.binding.RxList;
import com.ogaclejapan.rx.binding.RxProperty;
import com.ogaclejapan.rx.binding.RxReadOnlyList;
import com.ogaclejapan.rx.binding.RxReadOnlyProperty;

import android.content.Context;

import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.Subscriptions;

public class ArticleDetailViewModel extends AppViewModel {

    private final Articles mArticles;

    private final TagViewModel.Mapper mTagMapper;

    private final RxProperty<String> mTitle = RxProperty.create();

    private final RxProperty<String> mAuthorName = RxProperty.create();

    private final RxProperty<String> mAuthorThumbnailUrl = RxProperty.create();

    private final RxProperty<String> mCreatedAt = RxProperty.create();

    private final RxProperty<String> mContentHtml = RxProperty.create();

    private final RxProperty<String> mContentUrl = RxProperty.create();

    private final RxList<TagViewModel> mTags = RxList.create();

    protected ArticleDetailViewModel(Articles articles) {
        mArticles = articles;
        mTagMapper = new TagViewModel.Mapper();
    }

    public static ArticleDetailViewModel create(Context context) {
        return new ArticleDetailViewModel(Articles.with(context));
    }

    public RxReadOnlyProperty<String> title() {
        return mTitle;
    }

    public RxReadOnlyProperty<String> authorName() {
        return mAuthorName;
    }

    public RxReadOnlyProperty<String> authorThumbnailUrl() {
        return mAuthorThumbnailUrl;
    }

    public RxReadOnlyProperty<String> createdAt() {
        return mCreatedAt;
    }

    public RxReadOnlyProperty<String> contentHtml() {
        return mContentHtml;
    }

    public RxReadOnlyList<TagViewModel> tags() {
        return mTags;
    }

    public RxReadOnlyProperty<String> contentUrl() {
        return mContentUrl;
    }

    public void loadArticle(String id) {
        mArticles.loadById(id).subscribe(new Action1<Article>() {
            @Override
            public void call(final Article article) {

                Subscription s = Subscriptions.from(
                        mTitle.bind(article.title),
                        mAuthorName.bind(article.author.name),
                        mAuthorThumbnailUrl.bind(article.author.thumbnailUrl),
                        mCreatedAt.bind(article.createdAt, TIMEAGO),
                        mContentHtml.bind(article.html())
                );

                add(s);

                mContentUrl.set(article.url());
                mTags.addAll(Objects.map(article.tags, mTagMapper));

            }
        }, Rx.ERROR_ACTION_EMPTY);
    }

}
