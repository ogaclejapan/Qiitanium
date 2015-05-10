package com.ogaclejapan.qiitanium.presentation.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.norbsoft.typefacehelper.TypefaceHelper;
import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.activity.SettingsActivity;
import com.ogaclejapan.qiitanium.presentation.helper.SnackbarHelper;
import com.ogaclejapan.qiitanium.presentation.listener.Refreshable;
import com.ogaclejapan.qiitanium.util.AnimatorUtils;
import com.ogaclejapan.qiitanium.util.ViewUtils;

public class OverlayTopMenuView extends AppView<Refreshable> implements View.OnClickListener {

  private final SnackbarHelper snackbarHelper;
  private View layout;
  private View layoutToggle;
  private View homeBtn;
  private View homeLabel;
  private View refreshBtn;
  private View refreshLabel;
  private View settingBtn;
  private View settingLabel;

  public OverlayTopMenuView(Context context, AttributeSet attrs) {
    super(context, attrs);
    snackbarHelper = SnackbarHelper.with(context);
  }

  @Override
  protected void onViewCreated(View view) {
    layout = view.findViewById(R.id.overlay_top_menu);
    layoutToggle = view.findViewById(R.id.overlay_top_menu_toggle_btn);
    layoutToggle.setSelected(ViewUtils.isVisible(layout));

    homeBtn = view.findViewById(R.id.overlay_top_menu_logged_in_home_btn);
    homeLabel = view.findViewById(R.id.overlay_top_menu_logged_in_name_text);
    refreshBtn = view.findViewById(R.id.overlay_top_menu_refresh_btn);
    refreshLabel = view.findViewById(R.id.overlay_top_menu_refresh_text);
    settingBtn = view.findViewById(R.id.overlay_top_menu_settings_btn);
    settingLabel = view.findViewById(R.id.overlay_top_menu_settings_text);

    TypefaceHelper.typeface(homeLabel);
    TypefaceHelper.typeface(refreshLabel);
    TypefaceHelper.typeface(settingLabel);

    layoutToggle.setOnClickListener(this);
    homeBtn.setOnClickListener(this);
    refreshBtn.setOnClickListener(this);
    settingBtn.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.overlay_top_menu_toggle_btn:
        toggle();
        break;
      case R.id.overlay_top_menu_logged_in_home_btn:
        goHome();
        break;
      case R.id.overlay_top_menu_refresh_btn:
        refresh();
        turnOff();
        break;
      case R.id.overlay_top_menu_settings_btn:
        SettingsActivity.startActivity(getContext());
        turnOff();
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

    final float homeX = ViewUtils.getCenterX(homeBtn);
    final float refreshX = ViewUtils.getCenterX(refreshBtn);
    final float settingX = ViewUtils.getCenterX(settingBtn);

    refreshBtn.setTranslationX(homeX - refreshX);
    refreshBtn.setAlpha(0f);
    refreshBtn.setScaleX(0f);
    refreshBtn.setScaleY(0f);

    settingBtn.setTranslationX(homeX - settingX);
    settingBtn.setAlpha(0f);
    settingBtn.setScaleX(0f);
    settingBtn.setScaleY(0f);

    homeBtn.setAlpha(0f);
    homeBtn.setScaleX(0f);
    homeBtn.setScaleY(0f);

    homeLabel.setAlpha(0f);
    refreshLabel.setAlpha(0f);
    settingLabel.setAlpha(0f);

    AnimatorSet animSet = new AnimatorSet();

    animSet.playSequentially(
        AnimatorUtils.fadeIn(layout).setDuration(50),
        AnimatorUtils.together(
            new OvershootInterpolator(),
            AnimatorUtils.of(
                refreshBtn,
                AnimatorUtils.ofTranslationX(homeX - refreshX, 0f),
                AnimatorUtils.ofAlpha(0f, 1f),
                AnimatorUtils.ofScaleX(0f, 1f),
                AnimatorUtils.ofScaleY(0f, 1f)
            ).setDuration(200),
            AnimatorUtils.of(
                homeBtn,
                AnimatorUtils.ofAlpha(0f, 1f),
                AnimatorUtils.ofScaleX(0f, 1f),
                AnimatorUtils.ofScaleY(0f, 1f)
            ).setDuration(300),
            AnimatorUtils.of(
                settingBtn,
                AnimatorUtils.ofTranslationX(homeX - settingX, 0f),
                AnimatorUtils.ofAlpha(0f, 1f),
                AnimatorUtils.ofScaleX(0f, 1f),
                AnimatorUtils.ofScaleY(0f, 1f)
            ).setDuration(400)
        ),
        AnimatorUtils.together(
            new AccelerateInterpolator(),
            AnimatorUtils.fadeIn(homeLabel),
            AnimatorUtils.fadeIn(refreshLabel),
            AnimatorUtils.fadeIn(settingLabel)
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

    final float homeX = ViewUtils.getCenterX(homeBtn);
    final float refreshX = ViewUtils.getCenterX(refreshBtn);
    final float settingX = ViewUtils.getCenterX(settingBtn);

    AnimatorSet animSet = new AnimatorSet();

    animSet.playSequentially(
        AnimatorUtils.together(
            new DecelerateInterpolator(),
            AnimatorUtils.fadeOut(homeLabel),
            AnimatorUtils.fadeOut(refreshLabel),
            AnimatorUtils.fadeOut(settingLabel)
        ).setDuration(50),
        AnimatorUtils.together(
            new AnticipateInterpolator(),
            AnimatorUtils.of(
                refreshBtn,
                AnimatorUtils.ofTranslationX(0f, homeX - refreshX),
                AnimatorUtils.ofAlpha(1f, 0f),
                AnimatorUtils.ofScaleX(1f, 0f),
                AnimatorUtils.ofScaleY(1f, 0f)
            ).setDuration(400),
            AnimatorUtils.of(
                homeBtn,
                AnimatorUtils.ofAlpha(1f, 0f),
                AnimatorUtils.ofScaleX(1f, 0f),
                AnimatorUtils.ofScaleY(1f, 0f)
            ).setDuration(300),
            AnimatorUtils.of(
                settingBtn,
                AnimatorUtils.ofTranslationX(0f, homeX - settingX),
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
        AnimatorUtils.reset(refreshBtn);
        AnimatorUtils.reset(settingBtn);
      }
    });

    animSet.start();

  }

  public boolean canTurnOff() {
    return layoutToggle.isSelected();
  }

  private void goHome() {
    //TODO ログイン済みならユーザ画面、非ログインなら認証画面を表示する
    snackbarHelper.showTop(R.string.not_implemented);
  }

  private void refresh() {
    Refreshable refreshable = getItem();
    if (refreshable != null) {
      refreshable.refresh();
    }
  }

}
