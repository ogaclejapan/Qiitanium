package com.ogaclejapan.qiitanium.presentation.fragment;

import android.os.Bundle;
import android.view.View;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.helper.ScrollableScrollViewHelper;
import com.ogaclejapan.qiitanium.presentation.listener.ScrollableTab;
import com.ogaclejapan.qiitanium.presentation.widget.ObservableScrollView;
import com.ogaclejapan.qiitanium.util.ViewUtils;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItem;

public class ComingSoonScrollableFragment extends ComingSoonFragment
    implements ScrollableTab, ObservableScrollView.OnScrollListener {

  private ScrollableScrollViewHelper scrollViewHelper;

  public static ComingSoonScrollableFragment newInstance() {
    return new ComingSoonScrollableFragment();
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ObservableScrollView scrollView = ViewUtils.findById(view, R.id.coming_soon_scrollview);
    scrollViewHelper = ScrollableScrollViewHelper.with(getActivity(), scrollView);

    scrollView.setOnScrollListener(this);
  }

  @Override
  public void adjustScroll(int scrollY) {
    scrollViewHelper.adjustScroll(scrollY);
  }

  @Override
  public void onScrollChanged(int l, int t, int oldl, int oldt) {
    scrollViewHelper.onScroll(getPosition());
  }

  private int getPosition() {
    return FragmentPagerItem.getPosition(getArguments());
  }

}
