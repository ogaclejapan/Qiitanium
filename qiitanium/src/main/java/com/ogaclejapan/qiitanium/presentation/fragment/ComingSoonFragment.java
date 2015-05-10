package com.ogaclejapan.qiitanium.presentation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.norbsoft.typefacehelper.TypefaceHelper;
import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.util.ViewUtils;

public class ComingSoonFragment extends AppFragment {

  public static ComingSoonFragment newInstance() {
    return new ComingSoonFragment();
  }

  @Nullable
  @Override
  public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
      final Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_coming_soon, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    TypefaceHelper.typeface(ViewUtils.findById(view, R.id.coming_soon_title));
  }

}
