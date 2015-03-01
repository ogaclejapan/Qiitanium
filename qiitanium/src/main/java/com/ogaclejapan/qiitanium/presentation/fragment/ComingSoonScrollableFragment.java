package com.ogaclejapan.qiitanium.presentation.fragment;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.helper.ScrollableScrollViewHelper;
import com.ogaclejapan.qiitanium.presentation.listener.ScrollableTab;
import com.ogaclejapan.qiitanium.presentation.widget.ObservableScrollView;
import com.ogaclejapan.qiitanium.util.ViewUtils;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItem;

import android.os.Bundle;
import android.view.View;

public class ComingSoonScrollableFragment extends ComingSoonFragment
        implements ScrollableTab, ObservableScrollView.OnScrollListener {

    private ScrollableScrollViewHelper mScrollableHelper;

    public static ComingSoonScrollableFragment newInstance() {
        return new ComingSoonScrollableFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ObservableScrollView scrollView = ViewUtils.findById(view, R.id.coming_soon_scrollview);
        mScrollableHelper = ScrollableScrollViewHelper.with(getActivity(), scrollView);

        scrollView.setOnScrollListener(this);
    }

    @Override
    public void adjustScroll(int scrollY) {
        mScrollableHelper.adjustScroll(scrollY);
    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        mScrollableHelper.onScroll(getPosition());
    }

    private int getPosition() {
        return FragmentPagerItem.getPosition(getArguments());
    }

}
