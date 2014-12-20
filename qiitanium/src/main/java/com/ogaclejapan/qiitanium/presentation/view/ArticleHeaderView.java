package com.ogaclejapan.qiitanium.presentation.view;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.helper.PicassoHelper;
import com.ogaclejapan.qiitanium.presentation.viewmodel.ArticleDetailViewModel;
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

public class ArticleHeaderView extends AppView<ArticleDetailViewModel> {

    private Rx<TextView> mTitleText;
    private Rx<TextView> mCreatedAtText;
    private Rx<ImageView> mAuthorThumbnailImage;

    private PicassoHelper mPicassoHelper;
    private Transformation mRoundedTransformation;

    public ArticleHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            return;
        }
        mPicassoHelper = PicassoHelper.create(context);
        mRoundedTransformation = mPicassoHelper.roundedTransformation()
                .oval(true).borderWidth(1f).borderColor(R.color.divider).build();
    }

    @Override
    protected void onViewCreated(final View view) {
        mTitleText = RxView.findById(view, R.id.article_header_title);
        mCreatedAtText = RxView.findById(view, R.id.article_header_timeago);
        mAuthorThumbnailImage = RxView.findById(view, R.id.article_header_author_image);
    }

    @Override
    protected Subscription onBind(final ArticleDetailViewModel item) {
        return Subscriptions.from(
                mTitleText.bind(item.title(), RxActions.setText()),
                mCreatedAtText.bind(item.createdAt(), RxActions.setText()),
                mAuthorThumbnailImage.bind(item.authorThumbnailUrl(), loadThumbnailAction())
        );
    }

    protected RxAction<ImageView, String> loadThumbnailAction() {
        return new RxAction<ImageView, String>() {
            @Override
            public void call(final ImageView imageView, final String url) {
                mPicassoHelper
                        .load(url)
                        .placeholder(R.drawable.ic_account_circle_white_24dp)
                        .error(R.drawable.ic_account_circle_white_24dp)
                        .fit()
                        .transform(mRoundedTransformation)
                        .into(imageView);
            }
        };
    }

}
