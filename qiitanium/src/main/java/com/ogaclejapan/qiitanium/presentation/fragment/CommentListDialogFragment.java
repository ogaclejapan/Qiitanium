package com.ogaclejapan.qiitanium.presentation.fragment;

import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.adapter.CommentListAdapter;
import com.ogaclejapan.qiitanium.presentation.helper.LoadMoreHelper;
import com.ogaclejapan.qiitanium.presentation.viewmodel.CommentListViewModel;
import com.ogaclejapan.qiitanium.presentation.widget.TextProgressBar;
import com.ogaclejapan.qiitanium.util.Bundler;
import com.ogaclejapan.qiitanium.util.ViewUtils;
import com.ogaclejapan.rx.binding.Rx;
import com.ogaclejapan.rx.binding.RxActions;
import com.ogaclejapan.rx.binding.RxView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class CommentListDialogFragment extends AppDialogFragment
        implements AbsListView.OnScrollListener {

    public static final String TAG = CommentListDialogFragment.class.getSimpleName();

    private static final String KEY_ARTICLE_ID = TAG + ":article_id";

    public static CommentListDialogFragment newInstance(String articleId) {
        return new Bundler()
                .putString(KEY_ARTICLE_ID, articleId)
                .into(new CommentListDialogFragment());
    }

    private Rx<TextProgressBar> mProgressBar;

    private CommentListAdapter mListAdapter;
    private CommentListViewModel mViewModel;

    private LoadMoreHelper mLoadMoreHelper;

    public CommentListDialogFragment() {
        super(TAG);
    }

    private String getArticleId() {
        return getArguments().getString(KEY_ARTICLE_ID);
    }

    @Override
    protected View onSetupView(final LayoutInflater inflater, final Bundle savedInstanceState) {
        mListAdapter = CommentListAdapter.create(getContext());
        mViewModel = CommentListViewModel.create(getContext());
        mViewModel.setArticleId(getArticleId());

        final View view = inflater.inflate(R.layout.fragment_article_comment, null, false);

        mProgressBar = RxView.findById(view, R.id.progress);

        final ListView listView = ViewUtils.findById(view, R.id.list);
        listView.setEmptyView(view.findViewById(R.id.empty_container));
        listView.setOnScrollListener(this);

        final AnimationAdapter animAdapter = new ScaleInAnimationAdapter(mListAdapter);
        animAdapter.setAbsListView(listView);
        listView.setAdapter(animAdapter);

        mLoadMoreHelper = LoadMoreHelper.with(listView);

        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.loadMore();
    }

    @Override
    protected Subscription onBind() {
        return Subscriptions.from(
                mViewModel,
                mProgressBar.bind(mViewModel.isLoading(), RxActions.setVisibility()),
                mListAdapter.bind(mViewModel.items())
        );
    }

    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {
        //Do nothing.
    }

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem,
            final int visibleItemCount,
            final int totalItemCount) {

        if (ViewUtils.isVisible(mProgressBar.get())) {
            return;
        }

        if (mLoadMoreHelper.onNext(firstVisibleItem + visibleItemCount, totalItemCount)) {
            mViewModel.loadMore();
        }

    }

}
