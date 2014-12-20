package com.ogaclejapan.qiitanium.presentation.viewmodel;

import com.ogaclejapan.qiitanium.domain.model.Comment;
import com.ogaclejapan.rx.binding.RxProperty;
import com.ogaclejapan.rx.binding.RxReadOnlyProperty;

public class CommentViewModel extends AppViewModel {

    private final Comment mComment;
    private final RxProperty<String> mCreatedAt = RxProperty.create();

    protected CommentViewModel(Comment comment) {
        mComment = comment;
        mCreatedAt.bind(comment.createdAt, TIMEAGO);
    }

    public String id() {
        return mComment.id;
    }

    public RxReadOnlyProperty<String> text() {
        return mComment.text;
    }

    public RxReadOnlyProperty<String> createdAt() {
        return mCreatedAt;
    }

    public RxReadOnlyProperty<String> authorThumbnailUrl() {
        return mComment.author.thumbnailUrl;
    }

    public static class Mapper extends AppModelMapper<Comment, CommentViewModel> {

        @Override
        public CommentViewModel call(final Comment model) {
            return new CommentViewModel(model);
        }
    }
}
