package com.ogaclejapan.qiitanium.presentation.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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

import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class ArticleFragment extends AppFragment
    implements ObservableWebView.OnScrollListener {

  @SuppressWarnings("unused")
  public static final String TAG = ArticleFragment.class.getSimpleName();

  private ArticleDetailViewModel viewModel;
  private Rx<TextProgressBar> progressBar;
  private Rx<ObservableWebView> webView;
  private RxProperty<Boolean> isLoading = RxProperty.of(true);
  private ScrollableTabListener scrollableTabListener = null;
  private ViewModelHolder<ArticleDetailViewModel> viewModelHolder = null;

  public static ArticleFragment newInstance() {
    return new ArticleFragment();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof ScrollableTabListener) {
      scrollableTabListener = (ScrollableTabListener) activity;
    }
    if (activity instanceof ViewModelHolder) {
      viewModelHolder = (ViewModelHolder<ArticleDetailViewModel>) activity;
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

    progressBar = RxView.findById(view, R.id.progress);
    webView = RxView.findById(view, R.id.webview);

    final ObservableWebView webView = this.webView.get();
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
        isLoading.set(false);
      }
    });
    webView.setWebChromeClient(new WebChromeClient());
    webView.setOnScrollListener(this);

  }

  @Override
  protected Subscription onBind() {
    viewModel = viewModelHolder.get();

    return Subscriptions.from(
        viewModel,
        webView.bind(viewModel.contentHtml(), RxActions.loadDataWithBaseURL()),
        progressBar.bind(isLoading, RxActions.setVisibility())
    );
  }

  @Override
  public void onScrollChanged(int l, int t, int oldl, int oldt) {
    if (t == oldt) {
      return;
    }

    if (scrollableTabListener != null) {
      scrollableTabListener.onScroll(webView.get().getScrollY(), 0);
    }

  }

}
