package com.ogaclejapan.qiitanium.presentation.fragment;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.activity.TagActivity;
import com.ogaclejapan.qiitanium.presentation.adapter.TagListAdapter;
import com.ogaclejapan.qiitanium.presentation.helper.LoadMoreHelper;
import com.ogaclejapan.qiitanium.presentation.listener.Refreshable;
import com.ogaclejapan.qiitanium.presentation.viewmodel.TagListViewModel;
import com.ogaclejapan.qiitanium.presentation.viewmodel.TagViewModel;
import com.ogaclejapan.qiitanium.presentation.widget.TextProgressBar;
import com.ogaclejapan.qiitanium.util.ViewUtils;
import com.ogaclejapan.rx.binding.Rx;
import com.ogaclejapan.rx.binding.RxActions;
import com.ogaclejapan.rx.binding.RxView;
import com.twotoasters.jazzylistview.JazzyListView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class TagListFragment extends AppFragment
        implements AbsListView.OnScrollListener, AbsListView.OnItemClickListener, Refreshable {

    private Rx<TextProgressBar> mProgressBar;

    private JazzyListView mListView;

    private TagListAdapter mListAdapter;

    private TagListViewModel mViewModel;

    private LoadMoreHelper mLoadMoreHelper;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tag_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = TagListViewModel.create(getContext());
        mListAdapter = TagListAdapter.create(getContext());

        mProgressBar = RxView.findById(view, R.id.progress);

        mListView = ViewUtils.findById(view, R.id.list);

        mLoadMoreHelper = LoadMoreHelper.with(mListView);

        mListView.setAdapter(mListAdapter);

        mListView.setOnScrollListener(this);
        mListView.setOnItemClickListener(this);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh();
    }

    @Override
    protected Subscription onBind() {
        return Subscriptions.from(
                mViewModel,
                mListAdapter.bind(mViewModel.items()),
                mProgressBar.bind(mViewModel.isLoading(), RxActions.setVisibility())
        );
    }

    @Override
    public void refresh() {
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

        if (ViewUtils.isVisible(mProgressBar.get())) {
            return;
        }

        if (mLoadMoreHelper.onNext(firstVisibleItem + visibleItemCount, totalItemCount)) {
            mViewModel.loadMore();
        }

    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position,
            final long id) {
        final TagViewModel tag = mListAdapter.getItem(position);
        TagActivity.startActivity(getContext(), tag.id(), tag.name().get());
    }

}
