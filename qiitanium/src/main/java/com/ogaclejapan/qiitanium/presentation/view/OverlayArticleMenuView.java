package com.ogaclejapan.qiitanium.presentation.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.norbsoft.typefacehelper.TypefaceHelper;
import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.helper.SnackbarHelper;
import com.ogaclejapan.qiitanium.presentation.viewmodel.ArticleDetailViewModel;
import com.ogaclejapan.qiitanium.util.AnimatorUtils;
import com.ogaclejapan.qiitanium.util.IntentUtils;
import com.ogaclejapan.qiitanium.util.ViewUtils;

public class OverlayArticleMenuView extends AppView<ArticleDetailViewModel>
    implements View.OnClickListener {

  private final SnackbarHelper snackbarHelper;
  private View layout;
  private View layoutToggle;
  private View homeBtn;
  private View homeLabel;
  private View stockBtn;
  private View shareBtn;
  private View openBrowserBtn;
  private View commentBtn;

  public OverlayArticleMenuView(Context context, AttributeSet attrs) {
    super(context, attrs);
    snackbarHelper = SnackbarHelper.with(context);
  }

  @Override
  protected void onViewCreated(View view) {
    layout = view.findViewById(R.id.overlay_article_menu);
    layoutToggle = view.findViewById(R.id.overlay_article_menu_toggle_btn);
    layoutToggle.setSelected(ViewUtils.isVisible(layout));

    homeBtn = view.findViewById(R.id.overlay_article_menu_logged_in_home_btn);
    homeLabel = view.findViewById(R.id.overlay_article_menu_logged_in_name_text);
    stockBtn = view.findViewById(R.id.overlay_article_menu_stock_btn);
    shareBtn = view.findViewById(R.id.overlay_article_menu_share_btn);
    openBrowserBtn = view.findViewById(R.id.overlay_article_menu_open_browser_btn);
    commentBtn = view.findViewById(R.id.overlay_article_menu_comment_btn);

    TypefaceHelper.typeface(homeLabel);

    layoutToggle.setOnClickListener(this);
    homeBtn.setOnClickListener(this);
    stockBtn.setOnClickListener(this);
    shareBtn.setOnClickListener(this);
    openBrowserBtn.setOnClickListener(this);
    commentBtn.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.overlay_article_menu_toggle_btn:
        toggle();
        break;
      case R.id.overlay_article_menu_logged_in_home_btn:
        goHome();
        break;
      case R.id.overlay_article_menu_stock_btn:
        stock();
        break;
      case R.id.overlay_article_menu_share_btn:
        share();
        break;
      case R.id.overlay_article_menu_open_browser_btn:
        openBrowser();
        break;
      case R.id.overlay_article_menu_comment_btn:
        comment();
        break;
      default:
        //Do nothing.
    }
  }

  public void toggle() {
    if (canTurnOff()) {
      turnOff();
    } else {
      turnOn();
    }
  }

  public void turnOn() {

    ViewUtils.setVisible(layout);

    final float toggleX = ViewUtils.getCenterX(layoutToggle);
    final float toggleY = ViewUtils.getCenterY(layoutToggle);
    final float stockX = toggleX - ViewUtils.getCenterX(stockBtn);
    final float stockY = toggleY - ViewUtils.getCenterY(stockBtn);
    final float shareX = toggleX - ViewUtils.getCenterX(shareBtn);
    final float shareY = toggleY - ViewUtils.getCenterY(shareBtn);
    final float openBrowserX = toggleX - ViewUtils.getCenterX(openBrowserBtn);
    final float openBrowserY = toggleY - ViewUtils.getCenterY(openBrowserBtn);
    final float commentX = toggleX - ViewUtils.getCenterX(commentBtn);
    final float commentY = toggleY - ViewUtils.getCenterY(commentBtn);

    homeBtn.setAlpha(0f);
    homeBtn.setScaleX(0f);
    homeBtn.setScaleY(0f);
    homeLabel.setAlpha(0f);

    stockBtn.setTranslationX(stockX);
    stockBtn.setTranslationY(stockY);
    stockBtn.setAlpha(0f);
    stockBtn.setScaleX(0f);
    stockBtn.setScaleY(0f);

    shareBtn.setTranslationX(shareX);
    shareBtn.setTranslationY(shareY);
    shareBtn.setAlpha(0f);
    shareBtn.setScaleX(0f);
    shareBtn.setScaleY(0f);

    openBrowserBtn.setTranslationX(openBrowserX);
    openBrowserBtn.setTranslationY(openBrowserY);
    openBrowserBtn.setAlpha(0f);
    openBrowserBtn.setScaleX(0f);
    openBrowserBtn.setScaleY(0f);

    commentBtn.setTranslationX(commentX);
    commentBtn.setTranslationY(commentY);
    commentBtn.setAlpha(0f);
    commentBtn.setScaleX(0f);
    commentBtn.setScaleY(0f);

    AnimatorSet animSet = new AnimatorSet();

    animSet.playSequentially(
        AnimatorUtils.fadeIn(layout).setDuration(50),
        AnimatorUtils.together(
            new OvershootInterpolator(),
            AnimatorUtils.of(
                commentBtn,
                AnimatorUtils.ofTranslationX(commentX, 0f),
                AnimatorUtils.ofTranslationY(commentY, 0f),
                AnimatorUtils.ofAlpha(0f, 1f),
                AnimatorUtils.ofScaleX(0f, 1f),
                AnimatorUtils.ofScaleY(0f, 1f)
            ).setDuration(200),
            AnimatorUtils.of(
                openBrowserBtn,
                AnimatorUtils.ofTranslationX(openBrowserX, 0f),
                AnimatorUtils.ofTranslationY(openBrowserY, 0f),
                AnimatorUtils.ofAlpha(0f, 1f),
                AnimatorUtils.ofScaleX(0f, 1f),
                AnimatorUtils.ofScaleY(0f, 1f)
            ).setDuration(300),
            AnimatorUtils.of(
                shareBtn,
                AnimatorUtils.ofTranslationX(shareX, 0f),
                AnimatorUtils.ofTranslationY(shareY, 0f),
                AnimatorUtils.ofAlpha(0f, 1f),
                AnimatorUtils.ofScaleX(0f, 1f),
                AnimatorUtils.ofScaleY(0f, 1f)
            ).setDuration(400),
            AnimatorUtils.of(
                stockBtn,
                AnimatorUtils.ofTranslationX(stockX, 0f),
                AnimatorUtils.ofTranslationY(stockY, 0f),
                AnimatorUtils.ofAlpha(0f, 1f),
                AnimatorUtils.ofScaleX(0f, 1f),
                AnimatorUtils.ofScaleY(0f, 1f)
            ).setDuration(500),
            AnimatorUtils.of(
                homeBtn,
                AnimatorUtils.ofAlpha(0f, 1f),
                AnimatorUtils.ofScaleX(0f, 1f),
                AnimatorUtils.ofScaleY(0f, 1f)
            ).setDuration(500)
        ),
        AnimatorUtils.fadeIn(
            homeLabel,
            new DecelerateInterpolator()
        ).setDuration(50)
    );

    animSet.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        layoutToggle.setSelected(true);
      }
    });

    animSet.start();

  }

  public void turnOff() {

    final float toggleX = ViewUtils.getCenterX(layoutToggle);
    final float toggleY = ViewUtils.getCenterY(layoutToggle);
    final float stockX = toggleX - ViewUtils.getCenterX(stockBtn);
    final float stockY = toggleY - ViewUtils.getCenterY(stockBtn);
    final float shareX = toggleX - ViewUtils.getCenterX(shareBtn);
    final float shareY = toggleY - ViewUtils.getCenterY(shareBtn);
    final float openBrowserX = toggleX - ViewUtils.getCenterX(openBrowserBtn);
    final float openBrowserY = toggleY - ViewUtils.getCenterY(openBrowserBtn);
    final float commentX = toggleX - ViewUtils.getCenterX(commentBtn);
    final float commentY = toggleY - ViewUtils.getCenterY(commentBtn);

    AnimatorSet animSet = new AnimatorSet();

    animSet.playSequentially(
        AnimatorUtils.fadeOut(
            homeLabel,
            new DecelerateInterpolator()
        ).setDuration(50),
        AnimatorUtils.together(
            new AnticipateInterpolator(),
            AnimatorUtils.of(
                commentBtn,
                AnimatorUtils.ofTranslationX(0f, commentX),
                AnimatorUtils.ofTranslationY(0f, commentY),
                AnimatorUtils.ofAlpha(1f, 0f),
                AnimatorUtils.ofScaleX(1f, 0f),
                AnimatorUtils.ofScaleY(1f, 0f)
            ).setDuration(500),
            AnimatorUtils.of(
                openBrowserBtn,
                AnimatorUtils.ofTranslationX(0f, openBrowserX),
                AnimatorUtils.ofTranslationY(0f, openBrowserY),
                AnimatorUtils.ofAlpha(1f, 0f),
                AnimatorUtils.ofScaleX(1f, 0f),
                AnimatorUtils.ofScaleY(1f, 0f)
            ).setDuration(400),
            AnimatorUtils.of(
                shareBtn,
                AnimatorUtils.ofTranslationX(0f, shareX),
                AnimatorUtils.ofTranslationY(0f, shareY),
                AnimatorUtils.ofAlpha(1f, 0f),
                AnimatorUtils.ofScaleX(1f, 0f),
                AnimatorUtils.ofScaleY(1f, 0f)
            ).setDuration(300),
            AnimatorUtils.of(
                stockBtn,
                AnimatorUtils.ofTranslationX(0f, stockX),
                AnimatorUtils.ofTranslationY(0f, stockY),
                AnimatorUtils.ofAlpha(1f, 0f),
                AnimatorUtils.ofScaleX(1f, 0f),
                AnimatorUtils.ofScaleY(1f, 0f)
            ).setDuration(200),
            AnimatorUtils.of(
                homeBtn,
                AnimatorUtils.ofAlpha(1f, 0f),
                AnimatorUtils.ofScaleX(1f, 0f),
                AnimatorUtils.ofScaleY(1f, 0f)
            ).setDuration(200)
        ),
        AnimatorUtils.fadeOut(layout).setDuration(50)
    );

    animSet.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        layoutToggle.setSelected(false);
        ViewUtils.setInvisible(layout);
        AnimatorUtils.reset(homeBtn);
        AnimatorUtils.reset(commentBtn);
        AnimatorUtils.reset(openBrowserBtn);
        AnimatorUtils.reset(shareBtn);
        AnimatorUtils.reset(stockBtn);
      }
    });

    animSet.start();

  }

  public boolean canTurnOff() {
    return layoutToggle.isSelected();
  }

  private void goHome() {
    //TODO open native page
    snackbarHelper.showTop(R.string.not_implemented);
  }

  private void stock() {
    //TODO 認証機能を追加したら実装する
    snackbarHelper.showTop(R.string.not_implemented);
  }

  private void share() {
    turnOff();
    IntentUtils.share(getContext(), getItem().contentUrl().get(), getItem().title().get());
  }

  private void openBrowser() {
    turnOff();
    IntentUtils.openExternalUrl(getContext(), getItem().contentUrl().get());
  }

  private void comment() {
    //TODO 認証機能を追加したら実装する
    snackbarHelper.showTop(R.string.not_implemented);
  }

}
