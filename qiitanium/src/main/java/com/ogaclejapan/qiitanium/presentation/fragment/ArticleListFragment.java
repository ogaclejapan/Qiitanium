package com.ogaclejapan.qiitanium.presentation.fragment;

import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.activity.ArticleDetailActivity;
import com.ogaclejapan.qiitanium.presentation.adapter.ArticleListAdapter;
import com.ogaclejapan.qiitanium.presentation.helper.LoadMoreHelper;
import com.ogaclejapan.qiitanium.presentation.view.ArticleListItemView;
import com.ogaclejapan.qiitanium.presentation.viewmodel.ArticleListViewModel;
import com.ogaclejapan.qiitanium.presentation.viewmodel.ArticleViewModel;
import com.ogaclejapan.qiitanium.presentation.widget.MultiSwipeRefreshLayout;
import com.ogaclejapan.qiitanium.presentation.widget.TextProgressBar;
import com.ogaclejapan.qiitanium.util.Bundler;
import com.ogaclejapan.qiitanium.util.Objects;
import com.ogaclejapan.qiitanium.util.RxAppActions;
import com.ogaclejapan.qiitanium.util.ViewUtils;
import com.ogaclejapan.rx.binding.Rx;
import com.ogaclejapan.rx.binding.RxActions;
import com.ogaclejapan.rx.binding.RxView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import rx.Subscription;
import rx.subscriptions.Subscriptions;


public class ArticleListFragment extends AppFragment
        implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener,
        AbsListView.OnItemClickListener {

    public static final String TAG = ArticleListFragment.class.getSimpleName();

    private static final String KEY_TAG_ID = TAG + ":tag_id";

    public static ArticleListFragment newInstance() {
        return newInstance(null);
    }

    public static ArticleListFragment newInstance(String tagId) {
        return new Bundler()
                .putString(KEY_TAG_ID, tagId)
                .into(new ArticleListFragment());
    }

    private Rx<TextProgressBar> mProgressBar;
    private Rx<MultiSwipeRefreshLayout> mSwipeLayout;

    private ArticleListAdapter mListAdapter;
    private ArticleListViewModel mViewModel;

    private LoadMoreHelper mLoadMoreHelper;

    private String getTagId() {
        return getArguments().getString(KEY_TAG_ID);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListAdapter = ArticleListAdapter.create(getContext());
        mViewModel = ArticleListViewModel.create(getContext());
        mViewModel.setTagId(getTagId());

        mProgressBar = RxView.findById(view, R.id.progress);
        mSwipeLayout = RxView.findById(view, R.id.swiperefresh);

        final ListView listView = ViewUtils.findById(view, R.id.list);
        listView.setEmptyView(ViewUtils.findById(view, R.id.empty_container));
        listView.setOnScrollListener(this);
        listView.setOnItemClickListener(this);

        final AnimationAdapter animAdapter = new ScaleInAnimationAdapter(mListAdapter);
        animAdapter.setAbsListView(listView);
        listView.setAdapter(animAdapter);

        final MultiSwipeRefreshLayout swipeLayout = mSwipeLayout.get();
        swipeLayout.setColorSchemeResources(R.color.accent);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setSwipeableChildren(R.id.list, R.id.empty_container);

        mLoadMoreHelper = LoadMoreHelper.with(listView);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onRefresh();
    }

    @Override
    protected Subscription onBind() {
        return Subscriptions.from(
                mViewModel,
                mListAdapter.bind(mViewModel.items()),
                mProgressBar.bind(mViewModel.isLoading(), RxActions.setVisibility()),
                mSwipeLayout.bind(mViewModel.isRefreshing(), RxAppActions.setRefreshing())
        );
    }

    @Override
    public void onRefresh() {
        mViewModel.refresh();
    }

    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {
        //Do nothing.
    }

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem,
            final int visibleItemCount,
            final int totalItemCount) {

        if (mSwipeLayout.get().isRefreshing() || ViewUtils.isVisible(mProgressBar.get())) {
            return;
        }

        if (mLoadMoreHelper.onNext(firstVisibleItem + visibleItemCount, totalItemCount)) {
            mViewModel.loadMore();
        }

    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position,
            final long id) {
        final ArticleListItemView itemView = Objects.cast(view);
        final ArticleViewModel item = mListAdapter.getItem(position);
        final Intent intent = ArticleDetailActivity
                .intentOf(getContext(), item.id(), item.authorName().get());

        startActivity(intent, itemView.getActivityOptions(getActivity()).toBundle());
    }

}
