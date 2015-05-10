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

    private final Context context;
    private final FragmentManager fragmentManager;
    private final List<Item> items;

    public Builder(Context context, FragmentManager fm) {
      this.context = context;
      this.fragmentManager = fm;
      this.items = new ArrayList<Item>();
    }

    public static Builder with(Activity activity) {
      return new Builder(activity, activity.getFragmentManager());
    }

    public Builder add(Item item) {
      items.add(item);
      return this;
    }

    public Builder add(int id, Class<? extends Fragment> clazz) {
      return add(id, NO_TITLE, clazz);
    }

    public Builder add(int id, Class<? extends Fragment> clazz, Bundle args) {
      return add(id, NO_TITLE, clazz, args);
    }

    public Builder add(int id, int titleResId, Class<? extends Fragment> clazz) {
      return add(id, context.getString(titleResId), clazz);
    }

    public Builder add(int id, int titleResId, Class<? extends Fragment> clazz,
        final Bundle args) {
      return add(id, context.getString(titleResId), clazz, args);
    }

    public Builder add(int id, CharSequence title, Class<? extends Fragment> clazz) {
      return add(Item.of(id, title, clazz.getName()));
    }

    public Builder add(int id, CharSequence title, Class<? extends Fragment> clazz,
        Bundle args) {
      return add(Item.of(id, title, clazz.getName(), args));
    }

    public Adapter into(ViewPager pager) {
      return new Adapter(pager, fragmentManager, items);
    }

  }

  public static class Item {

    private final int id;
    private final CharSequence title;
    private final String fragmentName;
    private final Bundle args;

    private Item(int id, CharSequence title, String fname, Bundle args) {
      this.id = id;
      this.title = title;
      this.fragmentName = fname;
      this.args = args;
    }

    public static Item of(int id, CharSequence title, String fname) {
      return of(id, title, fname, new Bundle());
    }

    public static Item of(int id, CharSequence title, String fname, Bundle args) {
      return new FragmentPager.Item(id, title, fname, args);
    }

    public int id() {
      return id;
    }

    public CharSequence title() {
      return title;
    }

    public Fragment newInstance(Context context) {
      return Fragment.instantiate(context, fragmentName, args);
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
