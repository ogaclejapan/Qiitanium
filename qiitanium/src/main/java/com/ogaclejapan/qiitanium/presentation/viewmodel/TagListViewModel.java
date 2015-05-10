package com.ogaclejapan.qiitanium.presentation.viewmodel;

import android.content.Context;

import com.ogaclejapan.qiitanium.domain.model.Page;
import com.ogaclejapan.qiitanium.domain.model.Tag;
import com.ogaclejapan.qiitanium.domain.model.Tags;
import com.ogaclejapan.qiitanium.util.Objects;
import com.ogaclejapan.rx.binding.RxList;
import com.ogaclejapan.rx.binding.RxProperty;
import com.ogaclejapan.rx.binding.RxReadOnlyList;
import com.ogaclejapan.rx.binding.RxReadOnlyProperty;

import java.util.List;

import rx.Observer;
import rx.functions.Action1;
import timber.log.Timber;

public class TagListViewModel extends AppViewModel {

  private final Action1<TagViewModel> setParentAction =
      new Action1<TagViewModel>() {
        @Override
        public void call(TagViewModel itemViewModel) {
          itemViewModel.setParent(TagListViewModel.this);
        }
      };

  private final Page page = Page.create();
  private final Tags tags;
  private final RxProperty<Boolean> isLoading;
  private final RxProperty<Boolean> isRefreshing;
  private final RxList<TagViewModel> items;
  private final TagViewModel.Mapper itemMapper;

  protected TagListViewModel(Tags tags) {
    this.tags = tags;
    this.isRefreshing = RxProperty.of(false);
    this.isLoading = RxProperty.of(false);
    this.isLoading.bind(isRefreshing);
    this.items = RxList.create();
    this.itemMapper = new TagViewModel.Mapper();
  }

  public static TagListViewModel create(Context context) {
    return new TagListViewModel(Tags.with(context));
  }

  public RxReadOnlyProperty<Boolean> isLoading() {
    return isLoading;
  }

  public RxReadOnlyProperty<Boolean> isRefreshing() {
    return isRefreshing;
  }

  public RxReadOnlyList<TagViewModel> items() {
    return items;
  }

  public void refresh() {
    isRefreshing.set(true);
    page.reset();
    tags.list(page).subscribe(observerListTags());
  }

  public void loadMore() {
    if (page.isLast()) {
      return;
    }
    isLoading.set(true);
    tags.list(page).subscribe(observerListTags());
  }

  protected Observer<List<Tag>> observerListTags() {
    return new Observer<List<Tag>>() {
      @Override
      public void onCompleted() {
        isRefreshing.set(false);
      }

      @Override
      public void onError(final Throwable e) {
        isRefreshing.set(false);
        Timber.e(e, "Failed to load tag list.");
      }

      @Override
      public void onNext(final List<Tag> tags) {
        if (isRefreshing.get()) {
          items.clear();
        }
        items.addAll(Objects.map(tags, itemMapper, setParentAction));
      }
    };
  }

}
