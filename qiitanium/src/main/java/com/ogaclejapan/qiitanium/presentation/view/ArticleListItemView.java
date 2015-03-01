package com.ogaclejapan.qiitanium.presentation.view;

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

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class ArticleListItemView extends AppView<ArticleViewModel>
implements View.OnClickListener {

    private Rx<TextView> mTitleText;
    private Rx<TextView> mBodyText;
    private Rx<TextView> mCreatedAtText;
    private Rx<TextView> mAuthorNameText;
    private Rx<ImageView> mAuthorThumbnailImage;
    private Rx<ImageView> mBookmarkIcon;
    private Rx<TextView> mTagText;

    private PicassoHelper mPicassoHelper;
    private Transformation mRoundedTransformation;

    public ArticleListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            return;
        }

        mPicassoHelper = PicassoHelper.create(context);
        mRoundedTransformation = mPicassoHelper.roundedTransformation().oval(true).build();
    }

    @Override
    protected void onViewCreated(final View view) {
        mTitleText = RxView.findById(view, R.id.list_item_article_title);
        mBodyText = RxView.findById(view, R.id.list_item_article_body);
        mCreatedAtText = RxView.findById(view, R.id.list_item_article_timeago);
        mAuthorNameText = RxView.findById(view, R.id.list_item_article_author_text);
        mAuthorThumbnailImage = RxView.findById(view, R.id.list_item_article_author_image);
        mBookmarkIcon = RxView.findById(view, R.id.list_item_article_bookmark_icon);
        mTagText = RxView.findById(view, R.id.list_item_article_tag);

        // Apply custom font
        TypefaceHelper.typeface(mCreatedAtText.get());
        TypefaceHelper.typeface(mTagText.get());

        setOnClickListener(this);
    }

    @Override
    protected Subscription onBind(final ArticleViewModel item) {
        return Subscriptions.from(
                mTitleText.bind(item.title(), RxActions.setText()),
                mBodyText.bind(item.excerpt(), RxActions.setText()),
                mCreatedAtText.bind(item.createdAt(), RxActions.setText()),
                mAuthorNameText.bind(item.authorName(), RxActions.setText()),
                mAuthorThumbnailImage.bind(item.authorThumbnailUrl(), loadThumbnailAction()),
                mBookmarkIcon.bind(item.isBookmark(), RxActions.setVisibility()),
                mTagText.bind(item.tag(), RxActions.setText())
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
                mPicassoHelper
                        .load(url)
                        .placeholder(R.drawable.ic_person_outline_white_18dp)
                        .error(R.drawable.ic_person_outline_white_18dp)
                        .resizeDimen(R.dimen.thumbnail_small, R.dimen.thumbnail_small)
                        .transform(mRoundedTransformation)
                        .into(imageView);
            }
        };
    }

}
