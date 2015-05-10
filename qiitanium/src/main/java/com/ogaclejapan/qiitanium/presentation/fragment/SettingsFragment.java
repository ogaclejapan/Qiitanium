package com.ogaclejapan.qiitanium.presentation.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.ogaclejapan.qiitanium.BuildConfig;
import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.helper.ScrollableHelper;
import com.ogaclejapan.qiitanium.util.ViewUtils;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItem;

public class SettingsFragment extends PreferenceFragment
    implements Preference.OnPreferenceClickListener, AbsListView.OnScrollListener {

  private ScrollableHelper scrollableHelper;

  public static SettingsFragment newInstance() {
    return new SettingsFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.setting);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    Preference version = findPreferenceById(R.string.pref_setting_key_version);
    version.setSummary(BuildConfig.VERSION_NAME);

    Preference licenses = findPreferenceById(R.string.pref_setting_key_oss_licenses);
    licenses.setOnPreferenceClickListener(this);

    ListView listView = ViewUtils.findById(view, android.R.id.list);

    scrollableHelper = ScrollableHelper.with(getActivity(), listView);

    listView.setOnScrollListener(this);
  }

  @Override
  public boolean onPreferenceClick(Preference preference) {
    showOssLicenses();
    return true;
  }

  @Override
  public void onScrollStateChanged(AbsListView view, int scrollState) {
    //Do nothing.
  }

  @Override
  public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
      int totalItemCount) {
    scrollableHelper.onScroll(getPosition());
  }

  private void showOssLicenses() {
    LicenseDialogFragment.newInstance().show(getChildFragmentManager());
  }

  private Preference findPreferenceById(int resId) {
    return findPreference(getString(resId));
  }

  private int getPosition() {
    return FragmentPagerItem.getPosition(getArguments());
  }

}
