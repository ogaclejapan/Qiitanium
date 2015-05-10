package com.ogaclejapan.qiitanium.presentation.viewmodel;

import com.ogaclejapan.qiitanium.domain.model.Comment;
import com.ogaclejapan.rx.binding.RxProperty;
import com.ogaclejapan.rx.binding.RxReadOnlyProperty;

public class CommentViewModel extends AppViewModel {

  private final Comment comment;
  private final RxProperty<String> createdAt = RxProperty.create();

  protected CommentViewModel(Comment comment) {
    this.comment = comment;
    this.createdAt.bind(comment.createdAt, TIMEAGO);
  }

  public String id() {
    return comment.id;
  }

  public RxReadOnlyProperty<String> text() {
    return comment.text;
  }

  public RxReadOnlyProperty<String> createdAt() {
    return createdAt;
  }

  public RxReadOnlyProperty<String> authorThumbnailUrl() {
    return comment.author.thumbnailUrl;
  }

  public static class Mapper extends AppModelMapper<Comment, CommentViewModel> {

    @Override
    public CommentViewModel call(final Comment model) {
      return new CommentViewModel(model);
    }
  }
}
