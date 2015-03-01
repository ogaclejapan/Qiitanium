package com.ogaclejapan.qiitanium.presentation.viewmodel;

import com.ogaclejapan.qiitanium.domain.model.Comment;
import com.ogaclejapan.qiitanium.domain.model.Comments;
import com.ogaclejapan.qiitanium.domain.model.Page;
import com.ogaclejapan.qiitanium.presentation.activity.BootActivity;
import com.ogaclejapan.qiitanium.util.Objects;
import com.ogaclejapan.rx.binding.RxList;
import com.ogaclejapan.rx.binding.RxProperty;
import com.ogaclejapan.rx.binding.RxReadOnlyList;
import com.ogaclejapan.rx.binding.RxReadOnlyProperty;

import android.content.Context;

import java.util.List;

import rx.Observer;
import rx.functions.Action1;
import timber.log.Timber;

public class CommentListViewModel extends AppViewModel {

    private final Action1<CommentViewModel> mSetParentAction =
            new Action1<CommentViewModel>() {
                @Override
                public void call(CommentViewModel itemViewModel) {
                    itemViewModel.setParent(CommentListViewModel.this);
                }
            };

    private final Page mPage = Page.create();
    private final Comments mComments;

    private final RxProperty<Boolean> mIsEmpty;
    private final RxProperty<Boolean> mIsLoading;
    private final RxList<CommentViewModel> mItems;
    private final CommentViewModel.Mapper mItemMapper;

    private String mArticleId;

    protected CommentListViewModel(Comments comments) {
        mComments = comments;
        mIsLoading = RxProperty.of(false);
        mIsEmpty = RxProperty.of(true);
        mItems = RxList.create();
        mItemMapper = new CommentViewModel.Mapper();
    }

    public static CommentListViewModel create(Context context) {
        return new CommentListViewModel(Comments.with(context));
    }

    public void setArticleId(String articleId) {
        mArticleId = articleId;
    }

    public RxReadOnlyProperty<Boolean> isEmpty() {
        return mIsEmpty;
    }

    public RxReadOnlyProperty<Boolean> isLoading() {
        return mIsLoading;
    }

    public RxReadOnlyList<CommentViewModel> items() {
        return mItems;
    }

    public void loadMore() {
        if (mPage.isLast()) {
            return;
        }
        mIsLoading.set(true);
        mComments.loadAllByArticle(mArticleId, mPage).subscribe(observerListComment());
    }

    protected Observer<List<Comment>> observerListComment() {
        return new Observer<List<Comment>>() {
            @Override
            public void onCompleted() {
                mIsLoading.set(false);
                mIsEmpty.set(mItems.isEmpty());
            }

            @Override
            public void onError(final Throwable e) {
                mIsLoading.set(false);
                Timber.e(e, "Failed to load comment list.");
            }

            @Override
            public void onNext(final List<Comment> comments) {
                mItems.addAll(Objects.map(comments, mItemMapper, mSetParentAction));
            }
        };
    }

}
