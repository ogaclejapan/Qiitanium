package com.ogaclejapan.qiitanium.presentation.fragment;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.listener.ScrollableTabListener;
import com.ogaclejapan.qiitanium.presentation.listener.ViewModelHolder;
import com.ogaclejapan.qiitanium.presentation.viewmodel.ArticleDetailViewModel;
import com.ogaclejapan.qiitanium.presentation.widget.ObservableWebView;
import com.ogaclejapan.qiitanium.presentation.widget.TextProgressBar;
import com.ogaclejapan.qiitanium.util.IntentUtils;
import com.ogaclejapan.qiitanium.util.ViewUtils;
import com.ogaclejapan.rx.binding.Rx;
import com.ogaclejapan.rx.binding.RxActions;
import com.ogaclejapan.rx.binding.RxProperty;
import com.ogaclejapan.rx.binding.RxView;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class ArticleFragment extends AppFragment
        implements ObservableWebView.OnScrollListener {

    @SuppressWarnings("unused")
    public static final String TAG = ArticleFragment.class.getSimpleName();

    public static ArticleFragment newInstance() {
        return new ArticleFragment();
    }

    private ArticleDetailViewModel mViewModel;

    private Rx<TextProgressBar> mProgressBar;

    private Rx<ObservableWebView> mWebView;

    private RxProperty<Boolean> mIsLoading = RxProperty.of(true);

    private ScrollableTabListener mScrollableTabListener = null;

    private ViewModelHolder<ArticleDetailViewModel> mViewModelHolder = null;

    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ScrollableTabListener) {
            mScrollableTabListener = (ScrollableTabListener) activity;
        }
        if (activity instanceof ViewModelHolder) {
            mViewModelHolder = (ViewModelHolder<ArticleDetailViewModel>) activity;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
    protected Subscription onBind() {
        mViewModel = mViewModelHolder.get();

        return Subscriptions.from(
                mViewModel,
                mWebView.bind(mViewModel.contentHtml(), RxActions.loadDataWithBaseURL()),
                mProgressBar.bind(mIsLoading, RxActions.setVisibility())
        );
    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (t == oldt) {
            return;
        }

        if (mScrollableTabListener != null) {
            mScrollableTabListener.onScroll(mWebView.get().getScrollY(), 0);
        }

    }

}
