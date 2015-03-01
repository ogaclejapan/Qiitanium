package com.ogaclejapan.qiitanium.presentation.view;

import com.norbsoft.typefacehelper.TypefaceHelper;
import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.helper.SnackbarHelper;
import com.ogaclejapan.qiitanium.presentation.viewmodel.ArticleDetailViewModel;
import com.ogaclejapan.qiitanium.util.AnimatorUtils;
import com.ogaclejapan.qiitanium.util.IntentUtils;
import com.ogaclejapan.qiitanium.util.ViewUtils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

public class OverlayArticleMenuView extends AppView<ArticleDetailViewModel>
        implements View.OnClickListener {

    private final SnackbarHelper mSnackbarHelper;

    private View mLayout;

    private View mLayoutToggle;

    private View mHomeBtn;

    private View mHomeLabel;

    private View mStockBtn;

    private View mShareBtn;

    private View mOpenBrowserBtn;

    private View mCommentBtn;

    public OverlayArticleMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSnackbarHelper = SnackbarHelper.with(context);
    }

    @Override
    protected void onViewCreated(View view) {
        mLayout = view.findViewById(R.id.overlay_article_menu);
        mLayoutToggle = view.findViewById(R.id.overlay_article_menu_toggle_btn);
        mLayoutToggle.setSelected(ViewUtils.isVisible(mLayout));

        mHomeBtn = view.findViewById(R.id.overlay_article_menu_logged_in_home_btn);
        mHomeLabel = view.findViewById(R.id.overlay_article_menu_logged_in_name_text);
        mStockBtn = view.findViewById(R.id.overlay_article_menu_stock_btn);
        mShareBtn = view.findViewById(R.id.overlay_article_menu_share_btn);
        mOpenBrowserBtn = view.findViewById(R.id.overlay_article_menu_open_browser_btn);
        mCommentBtn = view.findViewById(R.id.overlay_article_menu_comment_btn);

        TypefaceHelper.typeface(mHomeLabel);

        mLayoutToggle.setOnClickListener(this);
        mHomeBtn.setOnClickListener(this);
        mStockBtn.setOnClickListener(this);
        mShareBtn.setOnClickListener(this);
        mOpenBrowserBtn.setOnClickListener(this);
        mCommentBtn.setOnClickListener(this);

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

        ViewUtils.setVisible(mLayout);

        final float toggleX = ViewUtils.getCenterX(mLayoutToggle);
        final float toggleY = ViewUtils.getCenterY(mLayoutToggle);
        final float stockX = toggleX - ViewUtils.getCenterX(mStockBtn);
        final float stockY = toggleY - ViewUtils.getCenterY(mStockBtn);
        final float shareX = toggleX - ViewUtils.getCenterX(mShareBtn);
        final float shareY = toggleY - ViewUtils.getCenterY(mShareBtn);
        final float openBrowserX = toggleX - ViewUtils.getCenterX(mOpenBrowserBtn);
        final float openBrowserY = toggleY - ViewUtils.getCenterY(mOpenBrowserBtn);
        final float commentX = toggleX - ViewUtils.getCenterX(mCommentBtn);
        final float commentY = toggleY - ViewUtils.getCenterY(mCommentBtn);

        mHomeBtn.setAlpha(0f);
        mHomeBtn.setScaleX(0f);
        mHomeBtn.setScaleY(0f);
        mHomeLabel.setAlpha(0f);

        mStockBtn.setTranslationX(stockX);
        mStockBtn.setTranslationY(stockY);
        mStockBtn.setAlpha(0f);
        mStockBtn.setScaleX(0f);
        mStockBtn.setScaleY(0f);

        mShareBtn.setTranslationX(shareX);
        mShareBtn.setTranslationY(shareY);
        mShareBtn.setAlpha(0f);
        mShareBtn.setScaleX(0f);
        mShareBtn.setScaleY(0f);

        mOpenBrowserBtn.setTranslationX(openBrowserX);
        mOpenBrowserBtn.setTranslationY(openBrowserY);
        mOpenBrowserBtn.setAlpha(0f);
        mOpenBrowserBtn.setScaleX(0f);
        mOpenBrowserBtn.setScaleY(0f);

        mCommentBtn.setTranslationX(commentX);
        mCommentBtn.setTranslationY(commentY);
        mCommentBtn.setAlpha(0f);
        mCommentBtn.setScaleX(0f);
        mCommentBtn.setScaleY(0f);

        AnimatorSet animSet = new AnimatorSet();

        animSet.playSequentially(
                AnimatorUtils.fadeIn(mLayout).setDuration(50),
                AnimatorUtils.together(
                        new OvershootInterpolator(),
                        AnimatorUtils.of(
                                mCommentBtn,
                                AnimatorUtils.ofTranslationX(commentX, 0f),
                                AnimatorUtils.ofTranslationY(commentY, 0f),
                                AnimatorUtils.ofAlpha(0f, 1f),
                                AnimatorUtils.ofScaleX(0f, 1f),
                                AnimatorUtils.ofScaleY(0f, 1f)
                        ).setDuration(200),
                        AnimatorUtils.of(
                                mOpenBrowserBtn,
                                AnimatorUtils.ofTranslationX(openBrowserX, 0f),
                                AnimatorUtils.ofTranslationY(openBrowserY, 0f),
                                AnimatorUtils.ofAlpha(0f, 1f),
                                AnimatorUtils.ofScaleX(0f, 1f),
                                AnimatorUtils.ofScaleY(0f, 1f)
                        ).setDuration(300),
                        AnimatorUtils.of(
                                mShareBtn,
                                AnimatorUtils.ofTranslationX(shareX, 0f),
                                AnimatorUtils.ofTranslationY(shareY, 0f),
                                AnimatorUtils.ofAlpha(0f, 1f),
                                AnimatorUtils.ofScaleX(0f, 1f),
                                AnimatorUtils.ofScaleY(0f, 1f)
                        ).setDuration(400),
                        AnimatorUtils.of(
                                mStockBtn,
                                AnimatorUtils.ofTranslationX(stockX, 0f),
                                AnimatorUtils.ofTranslationY(stockY, 0f),
                                AnimatorUtils.ofAlpha(0f, 1f),
                                AnimatorUtils.ofScaleX(0f, 1f),
                                AnimatorUtils.ofScaleY(0f, 1f)
                        ).setDuration(500),
                        AnimatorUtils.of(
                                mHomeBtn,
                                AnimatorUtils.ofAlpha(0f, 1f),
                                AnimatorUtils.ofScaleX(0f, 1f),
                                AnimatorUtils.ofScaleY(0f, 1f)
                        ).setDuration(500)
                ),
                AnimatorUtils.fadeIn(
                        mHomeLabel,
                        new DecelerateInterpolator()
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

        final float toggleX = ViewUtils.getCenterX(mLayoutToggle);
        final float toggleY = ViewUtils.getCenterY(mLayoutToggle);
        final float stockX = toggleX - ViewUtils.getCenterX(mStockBtn);
        final float stockY = toggleY - ViewUtils.getCenterY(mStockBtn);
        final float shareX = toggleX - ViewUtils.getCenterX(mShareBtn);
        final float shareY = toggleY - ViewUtils.getCenterY(mShareBtn);
        final float openBrowserX = toggleX - ViewUtils.getCenterX(mOpenBrowserBtn);
        final float openBrowserY = toggleY - ViewUtils.getCenterY(mOpenBrowserBtn);
        final float commentX = toggleX - ViewUtils.getCenterX(mCommentBtn);
        final float commentY = toggleY - ViewUtils.getCenterY(mCommentBtn);

        AnimatorSet animSet = new AnimatorSet();

        animSet.playSequentially(
                AnimatorUtils.fadeOut(
                        mHomeLabel,
                        new DecelerateInterpolator()
                ).setDuration(50),
                AnimatorUtils.together(
                        new AnticipateInterpolator(),
                        AnimatorUtils.of(
                                mCommentBtn,
                                AnimatorUtils.ofTranslationX(0f, commentX),
                                AnimatorUtils.ofTranslationY(0f, commentY),
                                AnimatorUtils.ofAlpha(1f, 0f),
                                AnimatorUtils.ofScaleX(1f, 0f),
                                AnimatorUtils.ofScaleY(1f, 0f)
                        ).setDuration(500),
                        AnimatorUtils.of(
                                mOpenBrowserBtn,
                                AnimatorUtils.ofTranslationX(0f, openBrowserX),
                                AnimatorUtils.ofTranslationY(0f, openBrowserY),
                                AnimatorUtils.ofAlpha(1f, 0f),
                                AnimatorUtils.ofScaleX(1f, 0f),
                                AnimatorUtils.ofScaleY(1f, 0f)
                        ).setDuration(400),
                        AnimatorUtils.of(
                                mShareBtn,
                                AnimatorUtils.ofTranslationX(0f, shareX),
                                AnimatorUtils.ofTranslationY(0f, shareY),
                                AnimatorUtils.ofAlpha(1f, 0f),
                                AnimatorUtils.ofScaleX(1f, 0f),
                                AnimatorUtils.ofScaleY(1f, 0f)
                        ).setDuration(300),
                        AnimatorUtils.of(
                                mStockBtn,
                                AnimatorUtils.ofTranslationX(0f, stockX),
                                AnimatorUtils.ofTranslationY(0f, stockY),
                                AnimatorUtils.ofAlpha(1f, 0f),
                                AnimatorUtils.ofScaleX(1f, 0f),
                                AnimatorUtils.ofScaleY(1f, 0f)
                        ).setDuration(200),
                        AnimatorUtils.of(
                                mHomeBtn,
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
                AnimatorUtils.reset(mCommentBtn);
                AnimatorUtils.reset(mOpenBrowserBtn);
                AnimatorUtils.reset(mShareBtn);
                AnimatorUtils.reset(mStockBtn);
            }
        });

        animSet.start();

    }

    public boolean canTurnOff() {
        return mLayoutToggle.isSelected();
    }

    private void goHome() {
        //TODO open native page
        mSnackbarHelper.showTop(R.string.not_implemented);
    }

    private void stock() {
        //TODO 認証機能を追加したら実装する
        mSnackbarHelper.showTop(R.string.not_implemented);
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
        mSnackbarHelper.showTop(R.string.not_implemented);
    }

}
