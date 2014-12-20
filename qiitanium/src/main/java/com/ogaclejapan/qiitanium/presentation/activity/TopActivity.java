package com.ogaclejapan.qiitanium.presentation.activity;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.fragment.ArticleListFragment;
import com.ogaclejapan.qiitanium.presentation.fragment.ComingSoonFragment;
import com.ogaclejapan.qiitanium.presentation.fragment.SettingsFragment;
import com.ogaclejapan.qiitanium.presentation.fragment.TagListFragment;
import com.ogaclejapan.qiitanium.presentation.helper.FragmentPager;
import com.ogaclejapan.qiitanium.presentation.helper.MaterialMenuDrawerToggle;
import com.ogaclejapan.qiitanium.presentation.view.DrawerMenuView;
import com.ogaclejapan.qiitanium.util.ContextUtils;
import com.ogaclejapan.qiitanium.util.ViewUtils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.Toolbar;

public class TopActivity extends AppActivity {

    @SuppressWarnings("unused")
    public static final String TAG = TopActivity.class.getSimpleName();

    private final ViewPager.SimpleOnPageChangeListener mPageChangeListener =
            new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(final int position) {
                    super.onPageSelected(position);
                    mDrawerToggle.closeDrawers();
                    updateTitle();
                }
            };

    private MaterialMenuDrawerToggle mDrawerToggle;
    private FragmentPager.Adapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        setActionBar(ViewUtils.<Toolbar>findById(this, R.id.toolbar));

        mDrawerToggle = MaterialMenuDrawerToggle.with(this);

        ViewPager viewpager = ViewUtils.findById(this, R.id.viewpager);
        viewpager.setOnPageChangeListener(mPageChangeListener);
        viewpager.setOffscreenPageLimit(3);

        mPagerAdapter = FragmentPager.Builder.with(this)
                .add(R.id.drawer_menu_feed, R.string.ab_title_feed, ArticleListFragment.class)
                .add(R.id.drawer_menu_tag, R.string.ab_title_tag, TagListFragment.class)
                .add(R.id.drawer_menu_mypage, R.string.ab_title_mypage, ComingSoonFragment.class)
                .add(R.id.drawer_menu_settings, R.string.ab_title_settings, SettingsFragment.class)
                .into(viewpager);

        DrawerMenuView drawerMenu = ViewUtils.findById(this, R.id.drawer_menu);
        drawerMenu.bindTo(mPagerAdapter);

        final View mainContainer = ViewUtils.findById(this, R.id.main_container);
        drawerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                int[] pos = new int[2];
                v.getLocationOnScreen(pos);
                int cx = (v.getLeft() + v.getRight()) / 2 + pos[0];
                int cy = (v.getTop() + v.getBottom()) / 2 + pos[1];
                int finalRadius = Math.max(mainContainer.getWidth(), mainContainer.getHeight());
                Animator anim = ViewAnimationUtils.createCircularReveal(mainContainer, cx, cy, 0,
                        finalRadius);
                anim.start();
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
        updateTitle();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private void updateTitle() {
        mDrawerToggle.getToolbar().setTitle(mPagerAdapter.getCurrentPageTitle());
    }

}
