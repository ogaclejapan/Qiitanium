package com.ogaclejapan.qiitanium.presentation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.norbsoft.typefacehelper.TypefaceHelper;
import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.helper.PicassoHelper;
import com.ogaclejapan.qiitanium.presentation.viewmodel.CommentViewModel;
import com.ogaclejapan.rx.binding.Rx;
import com.ogaclejapan.rx.binding.RxAction;
import com.ogaclejapan.rx.binding.RxActions;
import com.ogaclejapan.rx.binding.RxView;
import com.squareup.picasso.Transformation;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class CommentListItemView extends AppView<CommentViewModel> {

  private Rx<TextView> commentText;
  private Rx<ImageView> authorThumbImage;
  private Rx<TextView> commentedAtText;
  private PicassoHelper picassoHelper;
  private Transformation roundedTransformation;

  public CommentListItemView(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    if (isInEditMode()) {
      return;
    }

    picassoHelper = PicassoHelper.create(context);
    roundedTransformation = picassoHelper.roundedTransformation().oval(true).build();

  }

  @Override
  protected void onViewCreated(final View view) {
    commentText = RxView.findById(view, R.id.list_item_comment_text);
    authorThumbImage = RxView.findById(view, R.id.list_item_comment_author_image);
    commentedAtText = RxView.findById(view, R.id.list_item_comment_timeago);

    TypefaceHelper.typeface(commentedAtText.get());
  }

  @Override
  protected Subscription onBind(final CommentViewModel item) {
    return Subscriptions.from(
        commentText.bind(item.text(), RxActions.setText()),
        commentedAtText.bind(item.createdAt(), RxActions.setText()),
        authorThumbImage.bind(item.authorThumbnailUrl(), loadThumbnailAction())
    );
  }

  protected RxAction<ImageView, String> loadThumbnailAction() {
    return new RxAction<ImageView, String>() {
      @Override
      public void call(final ImageView imageView, final String url) {
        picassoHelper
            .load(url)
            .placeholder(R.drawable.ic_person_outline_white_24dp)
            .error(R.drawable.ic_person_outline_white_24dp)
            .resizeDimen(R.dimen.thumbnail_medium, R.dimen.thumbnail_medium)
            .transform(roundedTransformation)
            .into(imageView);
      }
    };
  }

}
