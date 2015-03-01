package com.ogaclejapan.qiitanium.presentation.activity;

import com.norbsoft.typefacehelper.TypefaceHelper;
import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.fragment.SettingsFragment;
import com.ogaclejapan.qiitanium.presentation.helper.ParallaxHeaderHelper;
import com.ogaclejapan.qiitanium.presentation.listener.ScrollableTabListener;
import com.ogaclejapan.qiitanium.util.ViewUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SettingsActivity extends AppActivity implements ScrollableTabListener {

    @SuppressWarnings("unused")
    public static final String TAG = SettingsActivity.class.getSimpleName();

    public static void startActivity(Context context) {
        context.startActivity(intentof(context));
    }

    public static Intent intentof(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    private final View.OnClickListener mOnBackListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            };

    private ParallaxHeaderHelper mHeaderHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
        setContentView(R.layout.activity_settings);

        View header = findViewById(R.id.header);
        View headerImage = findViewById(R.id.header_image);

        mHeaderHelper = new ParallaxHeaderHelper.Builder(header, headerImage)
                .setMinHeight(getDimensionPixelSize(R.dimen.header_min_height))
                .setElevation(getDimensionPixelSize(R.dimen.header_elevation))
                .create();

        TextView title = ViewUtils.findById(this, R.id.title);
        title.setText(R.string.app_name);

        TextView back = ViewUtils.findById(this, R.id.back);
        back.setText(R.string.back_to_prev);
        back.setOnClickListener(mOnBackListener);

        TypefaceHelper.typeface(title);
        TypefaceHelper.typeface(back);

        if (savedInstanceState == null) {
            setFragment(R.id.content, SettingsFragment.newInstance());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
        }
    }

    @Override
    public void onScroll(int scrollY, int position) {
        mHeaderHelper.scroll(scrollY);
    }

}
