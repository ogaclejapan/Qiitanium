package com.ogaclejapan.qiitanium.presentation.activity;

import com.norbsoft.typefacehelper.TypefaceHelper;
import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.fragment.ArticleAboutFragment;
import com.ogaclejapan.qiitanium.presentation.fragment.ArticleFragment;
import com.ogaclejapan.qiitanium.presentation.helper.ParallaxHeaderHelper;
import com.ogaclejapan.qiitanium.presentation.helper.SlidingUpPanelHelper;
import com.ogaclejapan.qiitanium.presentation.listener.ScrollableTabListener;
import com.ogaclejapan.qiitanium.presentation.listener.ViewModelHolder;
import com.ogaclejapan.qiitanium.presentation.view.OverlayArticleMenuView;
import com.ogaclejapan.qiitanium.presentation.viewmodel.ArticleDetailViewModel;
import com.ogaclejapan.qiitanium.util.ViewUtils;
import com.ogaclejapan.rx.binding.Rx;
import com.ogaclejapan.rx.binding.RxActions;
import com.ogaclejapan.rx.binding.RxView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class ArticleActivity extends AppActivity
        implements ScrollableTabListener, ViewModelHolder<ArticleDetailViewModel> {

    private static final String TAG = ArticleActivity.class.getSimpleName();
    private static final String KEY_ARTICLE_ID = TAG + ":article_id";

    public static void startActivity(Context context, String articleId) {
        context.startActivity(intentof(context, articleId));
    }

    public static Intent intentof(Context context, String articleId) {
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra(KEY_ARTICLE_ID, articleId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    private final View.OnClickListener mOnBackListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            };

    private ArticleDetailViewModel mViewModel;

    private OverlayArticleMenuView mArticleMenu;

    private ParallaxHeaderHelper mHeaderHelper;

    private SlidingUpPanelHelper mPanelHelper;

    private Rx<TextView> mTitle;
    private Rx<TextView> mTimeAgo;

    private View mSubject;
    private View mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
        setContentView(R.layout.activity_article);

        mViewModel = ArticleDetailViewModel.create(this);
        mViewModel.loadArticle(getArticleId());

        mArticleMenu = ViewUtils.findById(this, R.id.overlay_article);
        mArticleMenu.bindTo(mViewModel);

        View header = findViewById(R.id.header);
        View headerImage = findViewById(R.id.header_image);

        mHeaderHelper = new ParallaxHeaderHelper.Builder(header, headerImage)
                .setMinHeight(getDimensionPixelSize(R.dimen.header_min_height))
                .setElevation(getDimensionPixelSize(R.dimen.header_elevation))
                .create();

        SlidingUpPanelLayout slidingUpPanel = ViewUtils.findById(this, R.id.panel);
        mPanelHelper = new SlidingUpPanelHelper(slidingUpPanel);

        mSubject = ViewUtils.findById(this, R.id.header_article_subject);

        mTitle = RxView.findById(this, R.id.header_article_title);
        mTimeAgo = RxView.findById(this, R.id.header_article_timeago);

        mBack = ViewUtils.findById(this, R.id.back);
        mBack.setOnClickListener(mOnBackListener);
        mBack.setAlpha(0f);

        findViewById(R.id.back_icon).setOnClickListener(mOnBackListener);

        TypefaceHelper.typeface(mTimeAgo.get());
        TypefaceHelper.typeface(mBack);
        TypefaceHelper.typeface(findViewById(R.id.article_about_title));

        if (savedInstanceState == null) {
            setFragment(R.id.content, ArticleFragment.newInstance());
            setFragment(R.id.article_about_content, ArticleAboutFragment.newInstance(getArticleId()));
        }
    }

    @Override
    protected Subscription onBind() {
        return Subscriptions.from(
                mTimeAgo.bind(mViewModel.createdAt(), RxActions.setText()),
                mTitle.bind(mViewModel.title(), RxActions.setText())
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
        }
    }

    @Override
    public void onBackPressed() {
        if (mArticleMenu.canTurnOff()) {
            mArticleMenu.turnOff();
            return;
        }

        if (!mPanelHelper.isCollapsed()) {
            mPanelHelper.collapse();
            return;
        }

        super.onBackPressed();
    }

    @Override
    public void onScroll(int scrollY, int position) {
        float offset = mHeaderHelper.scroll(scrollY);
        mBack.setAlpha(offset);
        mSubject.setAlpha(1f - offset);
    }

    @Override
    public ArticleDetailViewModel get() {
        return mViewModel;
    }

    protected String getArticleId() {
        return getIntent().getStringExtra(KEY_ARTICLE_ID);
    }

}
