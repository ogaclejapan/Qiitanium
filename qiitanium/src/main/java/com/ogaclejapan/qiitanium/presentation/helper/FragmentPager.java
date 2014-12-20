package com.ogaclejapan.qiitanium.presentation.helper;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class FragmentPager {

    public static class Builder {

        private static final String NO_TITLE = "No Title";

        private final Context mContext;
        private final FragmentManager mFragmentManager;
        private final List<Item> mItems;

        public Builder(Context context, FragmentManager fm) {
            mContext = context;
            mFragmentManager = fm;
            mItems = new ArrayList<Item>();
        }

        public static Builder with(Activity activity) {
            return new Builder(activity, activity.getFragmentManager());
        }

        public Builder add(Item item) {
            mItems.add(item);
            return this;
        }

        public Builder add(int id, Class<? extends Fragment> clazz) {
            return add(id, NO_TITLE, clazz);
        }

        public Builder add(int id, Class<? extends Fragment> clazz, Bundle args) {
            return add(id, NO_TITLE, clazz, args);
        }

        public Builder add(int id, int titleResId, Class<? extends Fragment> clazz) {
            return add(id, mContext.getString(titleResId), clazz);
        }

        public Builder add(int id, int titleResId, Class<? extends Fragment> clazz,
                final Bundle args) {
            return add(id, mContext.getString(titleResId), clazz, args);
        }

        public Builder add(int id, CharSequence title, Class<? extends Fragment> clazz) {
            return add(Item.of(id, title, clazz.getName()));
        }

        public Builder add(int id, CharSequence title, Class<? extends Fragment> clazz,
                Bundle args) {
            return add(Item.of(id, title, clazz.getName(), args));
        }

        public Adapter into(ViewPager pager) {
            return new Adapter(pager, mFragmentManager, mItems);
        }

    }

    public static class Item {

        private final int mId;
        private final CharSequence mTitle;
        private final String mFragmentName;
        private final Bundle mArgs;

        private Item(int id, CharSequence title, String fname, Bundle args) {
            mId = id;
            mTitle = title;
            mFragmentName = fname;
            mArgs = args;
        }

        public static Item of(int id, CharSequence title, String fname) {
            return of(id, title, fname, new Bundle());
        }

        public static Item of(int id, CharSequence title, String fname, Bundle args) {
            return new FragmentPager.Item(id, title, fname, args);
        }

        public int id() {
            return mId;
        }

        public CharSequence title() {
            return mTitle;
        }

        public Fragment newInstance(Context context) {
            return Fragment.instantiate(context, mFragmentName, mArgs);
        }

    }

    public static class Adapter extends FragmentPagerAdapter {

        private final ViewPager mViewPager;
        private final List<Item> mItems;

        private Adapter(ViewPager viewpager, FragmentManager fm, List<Item> items) {
            super(fm);
            mItems = items;
            mViewPager = viewpager;
            mViewPager.setAdapter(this);
        }

        public ViewPager getViewPager() {
            return mViewPager;
        }

        @Override
        public Fragment getItem(int position) {
            return mItems.get(position).newInstance(mViewPager.getContext());
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mItems.get(position).title();
        }

        public CharSequence getCurrentPageTitle() {
            return getPageTitle(mViewPager.getCurrentItem());
        }

        public int getId(int position) {
            return mItems.get(position).id();
        }

        public int getCurrentId() {
            return getId(mViewPager.getCurrentItem());
        }

        public int getCurrentPosition() {
            return mViewPager.getCurrentItem();
        }

        public int getIndexOf(int id) {
            for (Item item : mItems) {
                if (item.id() == id) {
                    return mItems.indexOf(item);
                }
            }
            return -1;
        }

    }

}
