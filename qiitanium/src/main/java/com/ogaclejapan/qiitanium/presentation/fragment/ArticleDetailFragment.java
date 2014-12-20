package com.ogaclejapan.qiitanium.presentation.fragment;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.view.ArticleHeaderView;
import com.ogaclejapan.qiitanium.presentation.viewmodel.ArticleDetailViewModel;
import com.ogaclejapan.qiitanium.presentation.widget.ObservableWebView;
import com.ogaclejapan.qiitanium.presentation.widget.TextProgressBar;
import com.ogaclejapan.qiitanium.util.Bundler;
import com.ogaclejapan.qiitanium.util.ContextUtils;
import com.ogaclejapan.qiitanium.util.IntentUtils;
import com.ogaclejapan.qiitanium.util.ViewUtils;
import com.ogaclejapan.rx.binding.Rx;
import com.ogaclejapan.rx.binding.RxActions;
import com.ogaclejapan.rx.binding.RxProperty;
import com.ogaclejapan.rx.binding.RxView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class ArticleDetailFragment extends AppFragment
        implements ObservableWebView.OnScrollListener {

    public static final String TAG = ArticleDetailFragment.class.getSimpleName();
    private static final String KEY_ARTICLE_ID = TAG + ":article_id";

    public static ArticleDetailFragment newInstance(final String articleId) {
        return new Bundler()
                .putString(KEY_ARTICLE_ID, articleId)
                .into(new ArticleDetailFragment());
    }

    private ArticleDetailViewModel mViewModel;

    private Rx<TextProgressBar> mProgressBar;
    private Rx<ObservableWebView> mWebView;

    private RxProperty<Boolean> mIsLoading = RxProperty.of(true);

    private ArticleHeaderView mHeaderView;
    private int mHeaderHeight;
    private View mHeaderInnerLayout;

    private String getArticleId() {
        return getArguments().getString(KEY_ARTICLE_ID);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mHeaderHeight = ContextUtils.getDimensionPixelSize(getContext(),
                R.dimen.article_header_height);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = ArticleDetailViewModel.create(getContext());

        mHeaderInnerLayout = ViewUtils.findById(view, R.id.article_header_inner_layout);
        mHeaderView = ViewUtils.findById(view, R.id.article_header_layout);
        mHeaderView.bindTo(mViewModel);

        mProgressBar = RxView.findById(view, R.id.progress);
        mWebView = RxView.findById(view, R.id.webview);

        final ObservableWebView webView = mWebView.get();
        ViewUtils.setupWebView(webView);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                IntentUtils.openExternalUrl(getActivity(), url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mIsLoading.set(false);
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.setOnScrollListener(this);

    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.loadArticle(getArticleId());
    }

    @Override
    protected Subscription onBind() {
        return Subscriptions.from(
                mViewModel,
                mWebView.bind(mViewModel.contentHtml(), RxActions.loadDataWithBaseURL()),
                mProgressBar.bind(mIsLoading, RxActions.setVisibility())
        );
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.article_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_tag:
                onTagClicked();
                break;
            case R.id.menu_comment:
                onCommentClicked();
                break;
            case R.id.menu_share:
                onShareClicked();
                break;
            case R.id.menu_open_in_browser:
                onOpenInBrowserClicked();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (t == oldt) {
            return;
        }

        if (t == 0) {
            mHeaderView.setTranslationY(0f);
            mHeaderInnerLayout.setTranslationY(0f);
            return;
        }

        if (t > mHeaderHeight) {
            mHeaderView.setTranslationY(-mHeaderHeight);
            mHeaderInnerLayout.setTranslationY(mHeaderHeight / 2f);
            return;
        }

        final float hy = mHeaderView.getTranslationY();
        final int diff = oldt - t;
        final float y = Math.min(hy + (float) diff, 0f);
        mHeaderView.setTranslationY(y);
        mHeaderInnerLayout.setTranslationY(-y / 2f);
    }

    private void onTagClicked() {
        TagListDialogFragment.newInstance(getArticleId())
                .show(getChildFragmentManager());
    }

    private void onCommentClicked() {
        CommentListDialogFragment.newInstance(getArticleId())
                .show(getFragmentManager());
    }

    private void onShareClicked() {
        IntentUtils.share(getActivity(), mViewModel.contentUrl().get(), mViewModel.title().get());
    }

    private void onOpenInBrowserClicked() {
        IntentUtils.openExternalUrl(getActivity(), mViewModel.contentUrl().get());
    }

}
