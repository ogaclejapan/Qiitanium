package com.ogaclejapan.qiitanium.presentation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class ArticleActivity extends AppActivity
    implements ScrollableTabListener, ViewModelHolder<ArticleDetailViewModel> {

  private static final String TAG = ArticleActivity.class.getSimpleName();
  private static final String KEY_ARTICLE_ID = TAG + ":article_id";

  private final View.OnClickListener onBackListener =
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          finish();
        }
      };
  private ArticleDetailViewModel viewModel;
  private OverlayArticleMenuView articleMenu;
  private ParallaxHeaderHelper headerHelper;
  private SlidingUpPanelHelper panelHelper;
  private Rx<TextView> title;
  private Rx<TextView> timeAgo;
  private View subject;
  private View back;

  public static void startActivity(Context context, String articleId) {
    context.startActivity(intentof(context, articleId));
  }

  public static Intent intentof(Context context, String articleId) {
    Intent intent = new Intent(context, ArticleActivity.class);
    intent.putExtra(KEY_ARTICLE_ID, articleId);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
    setContentView(R.layout.activity_article);

    viewModel = ArticleDetailViewModel.create(this);
    viewModel.loadArticle(getArticleId());

    articleMenu = ViewUtils.findById(this, R.id.overlay_article);
    articleMenu.bindTo(viewModel);

    View header = findViewById(R.id.header);
    View headerImage = findViewById(R.id.header_image);

    headerHelper = new ParallaxHeaderHelper.Builder(header, headerImage)
        .setMinHeight(getDimensionPixelSize(R.dimen.header_min_height))
        .setElevation(getDimensionPixelSize(R.dimen.header_elevation))
        .create();

    SlidingUpPanelLayout slidingUpPanel = ViewUtils.findById(this, R.id.panel);
    panelHelper = new SlidingUpPanelHelper(slidingUpPanel);

    subject = ViewUtils.findById(this, R.id.header_article_subject);

    title = RxView.findById(this, R.id.header_article_title);
    timeAgo = RxView.findById(this, R.id.header_article_timeago);

    back = ViewUtils.findById(this, R.id.back);
    back.setOnClickListener(onBackListener);
    back.setAlpha(0f);

    findViewById(R.id.back_icon).setOnClickListener(onBackListener);

    TypefaceHelper.typeface(timeAgo.get());
    TypefaceHelper.typeface(back);
    TypefaceHelper.typeface(findViewById(R.id.article_about_title));

    if (savedInstanceState == null) {
      setFragment(R.id.content, ArticleFragment.newInstance());
      setFragment(R.id.article_about_content, ArticleAboutFragment.newInstance(getArticleId()));
    }
  }

  @Override
  protected Subscription onBind() {
    return Subscriptions.from(
        timeAgo.bind(viewModel.createdAt(), RxActions.setText()),
        title.bind(viewModel.title(), RxActions.setText())
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
    if (articleMenu.canTurnOff()) {
      articleMenu.turnOff();
      return;
    }

    if (!panelHelper.isCollapsed()) {
      panelHelper.collapse();
      return;
    }

    super.onBackPressed();
  }

  @Override
  public void onScroll(int scrollY, int position) {
    float offset = headerHelper.scroll(scrollY);
    back.setAlpha(offset);
    subject.setAlpha(1f - offset);
  }

  @Override
  public ArticleDetailViewModel get() {
    return viewModel;
  }

  protected String getArticleId() {
    return getIntent().getStringExtra(KEY_ARTICLE_ID);
  }

}
