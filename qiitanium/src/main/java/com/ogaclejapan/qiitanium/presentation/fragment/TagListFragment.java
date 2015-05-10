package com.ogaclejapan.qiitanium.presentation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

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

import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class TagListFragment extends AppFragment
    implements AbsListView.OnScrollListener, AbsListView.OnItemClickListener, Refreshable {

  private Rx<TextProgressBar> progressBar;
  private JazzyListView listView;
  private TagListAdapter listAdapter;
  private TagListViewModel viewModel;
  private LoadMoreHelper loadMoreHelper;

  @Nullable
  @Override
  public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
      final Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_tag_list, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    viewModel = TagListViewModel.create(getContext());
    listAdapter = TagListAdapter.create(getContext());

    progressBar = RxView.findById(view, R.id.progress);

    listView = ViewUtils.findById(view, R.id.list);

    loadMoreHelper = LoadMoreHelper.with(listView);

    listView.setAdapter(listAdapter);

    listView.setOnScrollListener(this);
    listView.setOnItemClickListener(this);

  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    refresh();
  }

  @Override
  protected Subscription onBind() {
    return Subscriptions.from(
        viewModel,
        listAdapter.bind(viewModel.items()),
        progressBar.bind(viewModel.isLoading(), RxActions.setVisibility())
    );
  }

  @Override
  public void refresh() {
    viewModel.refresh();
  }

  @Override
  public void onScrollStateChanged(final AbsListView view, final int scrollState) {
    //Do nothing.
  }

  @Override
  public void onScroll(final AbsListView view, final int firstVisibleItem,
      final int visibleItemCount,
      final int totalItemCount) {

    if (ViewUtils.isVisible(progressBar.get())) {
      return;
    }

    if (loadMoreHelper.onNext(firstVisibleItem + visibleItemCount, totalItemCount)) {
      viewModel.loadMore();
    }

  }

  @Override
  public void onItemClick(final AdapterView<?> parent, final View view, final int position,
      final long id) {
    final TagViewModel tag = listAdapter.getItem(position);
    TagActivity.startActivity(getContext(), tag.id(), tag.name().get());
  }

}
