package com.ogaclejapan.qiitanium.presentation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.norbsoft.typefacehelper.TypefaceHelper;
import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.activity.ArticleActivity;
import com.ogaclejapan.qiitanium.presentation.helper.PicassoHelper;
import com.ogaclejapan.qiitanium.presentation.viewmodel.ArticleViewModel;
import com.ogaclejapan.rx.binding.Rx;
import com.ogaclejapan.rx.binding.RxAction;
import com.ogaclejapan.rx.binding.RxActions;
import com.ogaclejapan.rx.binding.RxView;
import com.squareup.picasso.Transformation;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class ArticleListItemView extends AppView<ArticleViewModel>
    implements View.OnClickListener {

  private Rx<TextView> titleText;
  private Rx<TextView> bodyText;
  private Rx<TextView> createdAtText;
  private Rx<TextView> authorNameText;
  private Rx<ImageView> authorThumbnailImage;
  private Rx<ImageView> bookmarkIcon;
  private Rx<TextView> tagText;
  private PicassoHelper picassoHelper;
  private Transformation roundedTransformation;

  public ArticleListItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
    if (isInEditMode()) {
      return;
    }

    picassoHelper = PicassoHelper.create(context);
    roundedTransformation = picassoHelper.roundedTransformation().oval(true).build();
  }

  @Override
  protected void onViewCreated(final View view) {
    titleText = RxView.findById(view, R.id.list_item_article_title);
    bodyText = RxView.findById(view, R.id.list_item_article_body);
    createdAtText = RxView.findById(view, R.id.list_item_article_timeago);
    authorNameText = RxView.findById(view, R.id.list_item_article_author_text);
    authorThumbnailImage = RxView.findById(view, R.id.list_item_article_author_image);
    bookmarkIcon = RxView.findById(view, R.id.list_item_article_bookmark_icon);
    tagText = RxView.findById(view, R.id.list_item_article_tag);

    // Apply custom font
    TypefaceHelper.typeface(createdAtText.get());
    TypefaceHelper.typeface(tagText.get());

    setOnClickListener(this);
  }

  @Override
  protected Subscription onBind(final ArticleViewModel item) {
    return Subscriptions.from(
        titleText.bind(item.title(), RxActions.setText()),
        bodyText.bind(item.excerpt(), RxActions.setText()),
        createdAtText.bind(item.createdAt(), RxActions.setText()),
        authorNameText.bind(item.authorName(), RxActions.setText()),
        authorThumbnailImage.bind(item.authorThumbnailUrl(), loadThumbnailAction()),
        bookmarkIcon.bind(item.isBookmark(), RxActions.setVisibility()),
        tagText.bind(item.tag(), RxActions.setText())
    );
  }

  @Override
  public void onClick(View v) {
    final ArticleViewModel item = getItem();
    ArticleActivity.startActivity(getContext(), item.id());
  }

  protected RxAction<ImageView, String> loadThumbnailAction() {
    return new RxAction<ImageView, String>() {
      @Override
      public void call(final ImageView imageView, final String url) {
        picassoHelper
            .load(url)
            .placeholder(R.drawable.ic_person_outline_white_18dp)
            .error(R.drawable.ic_person_outline_white_18dp)
            .resizeDimen(R.dimen.thumbnail_small, R.dimen.thumbnail_small)
            .transform(roundedTransformation)
            .into(imageView);
      }
    };
  }

}
