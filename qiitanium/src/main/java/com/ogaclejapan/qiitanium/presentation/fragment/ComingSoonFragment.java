package com.ogaclejapan.qiitanium.presentation.fragment;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.util.ViewUtils;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ComingSoonFragment extends Fragment {

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
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView emptyText = ViewUtils.findById(view, R.id.empty);
        emptyText.setText(R.string.coming_soon);
    }
    
}
