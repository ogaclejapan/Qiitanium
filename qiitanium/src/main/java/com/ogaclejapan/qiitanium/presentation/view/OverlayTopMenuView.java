package com.ogaclejapan.qiitanium.presentation.view;

import com.norbsoft.typefacehelper.TypefaceHelper;
import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.activity.SettingsActivity;
import com.ogaclejapan.qiitanium.presentation.helper.SnackbarHelper;
import com.ogaclejapan.qiitanium.presentation.listener.Refreshable;
import com.ogaclejapan.qiitanium.util.AnimatorUtils;
import com.ogaclejapan.qiitanium.util.ViewUtils;

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

public class OverlayTopMenuView extends AppView<Refreshable> implements View.OnClickListener {

    private final SnackbarHelper mSnackbarHelper;

    private View mLayout;

    private View mLayoutToggle;

    private View mHomeBtn;

    private View mHomeLabel;

    private View mRefreshBtn;

    private View mRefreshLabel;

    private View mSettingBtn;

    private View mSettingLabel;

    public OverlayTopMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSnackbarHelper = SnackbarHelper.with(context);
    }

    @Override
    protected void onViewCreated(View view) {
        mLayout = view.findViewById(R.id.overlay_top_menu);
        mLayoutToggle = view.findViewById(R.id.overlay_top_menu_toggle_btn);
        mLayoutToggle.setSelected(ViewUtils.isVisible(mLayout));

        mHomeBtn = view.findViewById(R.id.overlay_top_menu_logged_in_home_btn);
        mHomeLabel = view.findViewById(R.id.overlay_top_menu_logged_in_name_text);
        mRefreshBtn = view.findViewById(R.id.overlay_top_menu_refresh_btn);
        mRefreshLabel = view.findViewById(R.id.overlay_top_menu_refresh_text);
        mSettingBtn = view.findViewById(R.id.overlay_top_menu_settings_btn);
        mSettingLabel = view.findViewById(R.id.overlay_top_menu_settings_text);

        TypefaceHelper.typeface(mHomeLabel);
        TypefaceHelper.typeface(mRefreshLabel);
        TypefaceHelper.typeface(mSettingLabel);

        mLayoutToggle.setOnClickListener(this);
        mHomeBtn.setOnClickListener(this);
        mRefreshBtn.setOnClickListener(this);
        mSettingBtn.setOnClickListener(this);

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

        ViewUtils.setVisible(mLayout);

        final float homeX = ViewUtils.getCenterX(mHomeBtn);
        final float refreshX = ViewUtils.getCenterX(mRefreshBtn);
        final float settingX = ViewUtils.getCenterX(mSettingBtn);

        mRefreshBtn.setTranslationX(homeX - refreshX);
        mRefreshBtn.setAlpha(0f);
        mRefreshBtn.setScaleX(0f);
        mRefreshBtn.setScaleY(0f);

        mSettingBtn.setTranslationX(homeX - settingX);
        mSettingBtn.setAlpha(0f);
        mSettingBtn.setScaleX(0f);
        mSettingBtn.setScaleY(0f);

        mHomeBtn.setAlpha(0f);
        mHomeBtn.setScaleX(0f);
        mHomeBtn.setScaleY(0f);

        mHomeLabel.setAlpha(0f);
        mRefreshLabel.setAlpha(0f);
        mSettingLabel.setAlpha(0f);

        AnimatorSet animSet = new AnimatorSet();

        animSet.playSequentially(
                AnimatorUtils.fadeIn(mLayout).setDuration(50),
                AnimatorUtils.together(
                        new OvershootInterpolator(),
                        AnimatorUtils.of(
                                mRefreshBtn,
                                AnimatorUtils.ofTranslationX(homeX - refreshX, 0f),
                                AnimatorUtils.ofAlpha(0f, 1f),
                                AnimatorUtils.ofScaleX(0f, 1f),
                                AnimatorUtils.ofScaleY(0f, 1f)
                        ).setDuration(200),
                        AnimatorUtils.of(
                                mHomeBtn,
                                AnimatorUtils.ofAlpha(0f, 1f),
                                AnimatorUtils.ofScaleX(0f, 1f),
                                AnimatorUtils.ofScaleY(0f, 1f)
                        ).setDuration(300),
                        AnimatorUtils.of(
                                mSettingBtn,
                                AnimatorUtils.ofTranslationX(homeX - settingX, 0f),
                                AnimatorUtils.ofAlpha(0f, 1f),
                                AnimatorUtils.ofScaleX(0f, 1f),
                                AnimatorUtils.ofScaleY(0f, 1f)
                        ).setDuration(400)
                ),
                AnimatorUtils.together(
                        new AccelerateInterpolator(),
                        AnimatorUtils.fadeIn(mHomeLabel),
                        AnimatorUtils.fadeIn(mRefreshLabel),
                        AnimatorUtils.fadeIn(mSettingLabel)
                ).setDuration(50)
        );

        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLayoutToggle.setSelected(true);
            }
        });

        animSet.start();

    }

    public void turnOff() {

        final float homeX = ViewUtils.getCenterX(mHomeBtn);
        final float refreshX = ViewUtils.getCenterX(mRefreshBtn);
        final float settingX = ViewUtils.getCenterX(mSettingBtn);

        AnimatorSet animSet = new AnimatorSet();

        animSet.playSequentially(
                AnimatorUtils.together(
                        new DecelerateInterpolator(),
                        AnimatorUtils.fadeOut(mHomeLabel),
                        AnimatorUtils.fadeOut(mRefreshLabel),
                        AnimatorUtils.fadeOut(mSettingLabel)
                ).setDuration(50),
                AnimatorUtils.together(
                        new AnticipateInterpolator(),
                        AnimatorUtils.of(
                                mRefreshBtn,
                                AnimatorUtils.ofTranslationX(0f, homeX - refreshX),
                                AnimatorUtils.ofAlpha(1f, 0f),
                                AnimatorUtils.ofScaleX(1f, 0f),
                                AnimatorUtils.ofScaleY(1f, 0f)
                        ).setDuration(400),
                        AnimatorUtils.of(
                                mHomeBtn,
                                AnimatorUtils.ofAlpha(1f, 0f),
                                AnimatorUtils.ofScaleX(1f, 0f),
                                AnimatorUtils.ofScaleY(1f, 0f)
                        ).setDuration(300),
                        AnimatorUtils.of(
                                mSettingBtn,
                                AnimatorUtils.ofTranslationX(0f, homeX - settingX),
                                AnimatorUtils.ofAlpha(1f, 0f),
                                AnimatorUtils.ofScaleX(1f, 0f),
                                AnimatorUtils.ofScaleY(1f, 0f)
                        ).setDuration(200)
                ),
                AnimatorUtils.fadeOut(mLayout).setDuration(50)
        );

        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLayoutToggle.setSelected(false);
                ViewUtils.setInvisible(mLayout);
                AnimatorUtils.reset(mHomeBtn);
                AnimatorUtils.reset(mRefreshBtn);
                AnimatorUtils.reset(mSettingBtn);
            }
        });

        animSet.start();

    }

    public boolean canTurnOff() {
        return mLayoutToggle.isSelected();
    }

    private void goHome() {
        //TODO ログイン済みならユーザ画面、非ログインなら認証画面を表示する
        mSnackbarHelper.showTop(R.string.not_implemented);
    }

    private void refresh() {
        Refreshable refreshable = getItem();
        if (refreshable != null) {
            refreshable.refresh();
        }
    }


}
