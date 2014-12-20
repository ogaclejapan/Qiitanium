package com.ogaclejapan.qiitanium.presentation.fragment;

import com.ogaclejapan.qiitanium.BuildConfig;
import com.ogaclejapan.qiitanium.R;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.View;

public class SettingsFragment extends PreferenceFragment
        implements Preference.OnPreferenceClickListener {

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
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        showOssLicenses();
        return true;
    }

    private void showOssLicenses() {
        LicenseDialogFragment.newInstance().show(getChildFragmentManager());
    }

    private Preference findPreferenceById(int resId) {
        return findPreference(getString(resId));
    }
}
