package com.ogaclejapan.qiitanium.presentation.viewmodel;

import com.ogaclejapan.qiitanium.domain.model.Page;
import com.ogaclejapan.qiitanium.domain.model.Tag;
import com.ogaclejapan.qiitanium.domain.model.Tags;
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

public class TagListViewModel extends AppViewModel {

    private final Action1<TagViewModel> mSetParentAction =
            new Action1<TagViewModel>() {
                @Override
                public void call(TagViewModel itemViewModel) {
                    itemViewModel.setParent(TagListViewModel.this);
                }
            };

    private final Page mPage = Page.create();
    private final Tags mTags;

    private final RxProperty<Boolean> mIsLoading;
    private final RxProperty<Boolean> mIsRefreshing;
    private final RxList<TagViewModel> mItems;
    private final TagViewModel.Mapper mItemMapper;

    protected TagListViewModel(Tags tags) {
        mTags = tags;
        mIsRefreshing = RxProperty.of(false);
        mIsLoading = RxProperty.of(false);
        mIsLoading.bind(mIsRefreshing);
        mItems = RxList.create();
        mItemMapper = new TagViewModel.Mapper();
    }

    public static TagListViewModel create(Context context) {
        return new TagListViewModel(Tags.with(context));
    }

    public RxReadOnlyProperty<Boolean> isLoading() {
        return mIsLoading;
    }

    public RxReadOnlyProperty<Boolean> isRefreshing() {
        return mIsRefreshing;
    }

    public RxReadOnlyList<TagViewModel> items() {
        return mItems;
    }

    public void refresh() {
        mIsRefreshing.set(true);
        mPage.reset();
        mTags.list(mPage).subscribe(observerListTags());
    }

    public void loadMore() {
        if (mPage.isLast()) {
            return;
        }
        mIsLoading.set(true);
        mTags.list(mPage).subscribe(observerListTags());
    }

    protected Observer<List<Tag>> observerListTags() {
        return new Observer<List<Tag>>() {
            @Override
            public void onCompleted() {
                mIsRefreshing.set(false);
            }

            @Override
            public void onError(final Throwable e) {
                mIsRefreshing.set(false);
                Timber.e(e, "Failed to load tag list.");
            }

            @Override
            public void onNext(final List<Tag> tags) {
                if (mIsRefreshing.get()) {
                    mItems.clear();
                }
                mItems.addAll(Objects.map(tags, mItemMapper, mSetParentAction));
            }
        };
    }

}
