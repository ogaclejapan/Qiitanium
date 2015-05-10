package com.ogaclejapan.qiitanium.presentation.viewmodel;

import android.content.Context;

import com.ogaclejapan.qiitanium.domain.model.Comment;
import com.ogaclejapan.qiitanium.domain.model.Comments;
import com.ogaclejapan.qiitanium.domain.model.Page;
import com.ogaclejapan.qiitanium.util.Objects;
import com.ogaclejapan.rx.binding.RxList;
import com.ogaclejapan.rx.binding.RxProperty;
import com.ogaclejapan.rx.binding.RxReadOnlyList;
import com.ogaclejapan.rx.binding.RxReadOnlyProperty;

import java.util.List;

import rx.Observer;
import rx.functions.Action1;
import timber.log.Timber;

public class CommentListViewModel extends AppViewModel {

  private final Action1<CommentViewModel> setParentAction =
      new Action1<CommentViewModel>() {
        @Override
        public void call(CommentViewModel itemViewModel) {
          itemViewModel.setParent(CommentListViewModel.this);
        }
      };

  private final Page page = Page.create();
  private final Comments comments;
  private final RxProperty<Boolean> isEmpty;
  private final RxProperty<Boolean> isLoading;
  private final RxList<CommentViewModel> items;
  private final CommentViewModel.Mapper itemMapper;
  private String articleId;

  protected CommentListViewModel(Comments comments) {
    this.comments = comments;
    this.isLoading = RxProperty.of(false);
    this.isEmpty = RxProperty.of(true);
    this.items = RxList.create();
    this.itemMapper = new CommentViewModel.Mapper();
  }

  public static CommentListViewModel create(Context context) {
    return new CommentListViewModel(Comments.with(context));
  }

  public void setArticleId(String articleId) {
    this.articleId = articleId;
  }

  public RxReadOnlyProperty<Boolean> isEmpty() {
    return isEmpty;
  }

  public RxReadOnlyProperty<Boolean> isLoading() {
    return isLoading;
  }

  public RxReadOnlyList<CommentViewModel> items() {
    return items;
  }

  public void loadMore() {
    if (page.isLast()) {
      return;
    }
    isLoading.set(true);
    comments.loadAllByArticle(articleId, page).subscribe(observerListComment());
  }

  protected Observer<List<Comment>> observerListComment() {
    return new Observer<List<Comment>>() {
      @Override
      public void onCompleted() {
        isLoading.set(false);
        isEmpty.set(items.isEmpty());
      }

      @Override
      public void onError(final Throwable e) {
        isLoading.set(false);
        Timber.e(e, "Failed to load comment list.");
      }

      @Override
      public void onNext(final List<Comment> comments) {
        items.addAll(Objects.map(comments, itemMapper, setParentAction));
      }
    };
  }

}
