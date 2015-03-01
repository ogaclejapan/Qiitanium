package com.ogaclejapan.qiitanium.presentation.activity;

import com.norbsoft.typefacehelper.TypefaceHelper;
import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.fragment.ArticleListFragment;
import com.ogaclejapan.qiitanium.presentation.fragment.ComingSoonFragment;
import com.ogaclejapan.qiitanium.presentation.fragment.ComingSoonScrollableFragment;
import com.ogaclejapan.qiitanium.presentation.fragment.TagListFragment;
import com.ogaclejapan.qiitanium.presentation.helper.ParallaxHeaderHelper;
import com.ogaclejapan.qiitanium.presentation.helper.SlidingUpPanelHelper;
import com.ogaclejapan.qiitanium.presentation.listener.Refreshable;
import com.ogaclejapan.qiitanium.presentation.listener.ScrollableTab;
import com.ogaclejapan.qiitanium.presentation.listener.ScrollableTabListener;
import com.ogaclejapan.qiitanium.presentation.view.OverlayTopMenuView;
import com.ogaclejapan.qiitanium.util.ViewUtils;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItems;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

public class TopActivity extends AppActivity
        implements ScrollableTabListener, Refreshable {

    public static void startActivity(Context context) {
        context.startActivity(intentOf(context));
    }

    public static Intent intentOf(Context context) {
        return new Intent(context, TopActivity.class);
    }

    private final ViewPager.SimpleOnPageChangeListener mPageChangeListener =
            new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    Fragment f = mViewPagerAdapter.getPage(position);
                    if (f instanceof ScrollableTab) {
                        ((ScrollableTab) f).adjustScroll(mHeaderHelper.getTranslationY());
                    }
                }
            };

    private OverlayTopMenuView mTopMenu;
    private ParallaxHeaderHelper mHeaderHelper;
    private SlidingUpPanelHelper mPanelHelper;
    private ViewPager mViewPager;
    private FragmentPagerItemAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        FragmentManager fm = getFragmentManager();

        View header = findViewById(R.id.header);
        View headerImage = findViewById(R.id.header_image);

        mHeaderHelper = new ParallaxHeaderHelper.Builder(header, headerImage)
                .setMinHeight(getDimensionPixelSize(R.dimen.header_min_height))
                .setElevation(getDimensionPixelSize(R.dimen.header_elevation))
                .create();

        SlidingUpPanelLayout slidingUpPanel = ViewUtils.findById(this, R.id.panel);
        mPanelHelper = new SlidingUpPanelHelper(slidingUpPanel);

        TextView title = ViewUtils.findById(this, R.id.title);
        title.setText(R.string.label_articles);

        mViewPagerAdapter = new FragmentPagerItemAdapter(fm, FragmentPagerItems.with(this)
                .add(R.string.tab_new, ArticleListFragment.class)
                .add(R.string.tab_following, ComingSoonScrollableFragment.class)
                .create());

        mViewPager = ViewUtils.findById(this, R.id.article_top_viewpager);
        mViewPager.setAdapter(mViewPagerAdapter);
        SmartTabLayout viewPagerTab = ViewUtils.findById(this, R.id.viewpagertab);
        viewPagerTab.setCustomTabView(R.layout.view_tab, R.id.view_tab_text);
        viewPagerTab.setViewPager(mViewPager);
        viewPagerTab.setOnPageChangeListener(mPageChangeListener);

        TextView tagTitle = ViewUtils.findById(this, R.id.tag_top_title);
        ViewPager tagViewPager = ViewUtils.findById(this, R.id.tag_top_viewpager);
        tagViewPager.setAdapter(new FragmentPagerItemAdapter(fm, FragmentPagerItems.with(this)
                .add(R.string.tab_popular, TagListFragment.class)
                .add(R.string.tab_following, ComingSoonFragment.class)
                .create()));

        SmartTabLayout tagViewPagerTab = ViewUtils.findById(this, R.id.tag_top_viewpagertab);
        tagViewPagerTab.setViewPager(tagViewPager);

        mTopMenu = ViewUtils.findById(this, R.id.overlay_top);
        mTopMenu.bindTo(this);

        TypefaceHelper.typeface(title);
        TypefaceHelper.typeface(tagTitle);
        TypefaceHelper.typeface(viewPagerTab);
        TypefaceHelper.typeface(tagViewPagerTab);

    }

    @Override
    public void onBackPressed() {
        if (mTopMenu.canTurnOff()) {
            mTopMenu.turnOff();
            return;
        }

        if (!mPanelHelper.isCollapsed()) {
            mPanelHelper.collapse();
            return;
        }

        super.onBackPressed();
    }


    @Override
    public void onScroll(int scrollY, int position) {
        if (mViewPager.getCurrentItem() != position) {
            return;
        }

        mHeaderHelper.scroll(scrollY);
    }

    @Override
    public void refresh() {
        final Fragment f = mViewPagerAdapter.getPage(mViewPager.getCurrentItem());
        if (f instanceof Refreshable) {
            ((Refreshable) f).refresh();
        }
    }

}
