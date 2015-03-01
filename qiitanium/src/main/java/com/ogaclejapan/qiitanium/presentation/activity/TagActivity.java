package com.ogaclejapan.qiitanium.presentation.activity;

import com.norbsoft.typefacehelper.TypefaceHelper;
import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.fragment.ArticleListFragment;
import com.ogaclejapan.qiitanium.presentation.helper.ParallaxHeaderHelper;
import com.ogaclejapan.qiitanium.presentation.listener.ScrollableTabListener;
import com.ogaclejapan.qiitanium.util.ViewUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TagActivity extends AppActivity
        implements ScrollableTabListener {

    public static final String TAG = TagActivity.class.getSimpleName();
    private static final String KEY_TAG_ID = TAG + ":tag_id";
    private static final String KEY_TAG_NAME = TAG + ":tag_name";

    public static void startActivity(Context context, String tagId, String tagName) {
        context.startActivity(intentof(context, tagId, tagName));
    }

    public static Intent intentof(Context context, String tagId, String tagName) {
        Intent intent = new Intent(context, TagActivity.class);
        intent.putExtra(KEY_TAG_ID, tagId);
        intent.putExtra(KEY_TAG_NAME, tagName);
        return intent;
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
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
        setContentView(R.layout.activity_tag);

        View header = findViewById(R.id.header);
        View headerImage = findViewById(R.id.header_image);

        mHeaderHelper = new ParallaxHeaderHelper.Builder(header, headerImage)
                .setMinHeight(getDimensionPixelSize(R.dimen.header_min_height))
                .setElevation(getDimensionPixelSize(R.dimen.header_elevation))
                .create();

        TextView title = ViewUtils.findById(this, R.id.title);
        title.setText(getTagName());

        TextView back = ViewUtils.findById(this, R.id.back);
        back.setOnClickListener(mOnBackListener);

        TypefaceHelper.typeface(title);
        TypefaceHelper.typeface(back);

        if (savedInstanceState == null) {
            setFragment(R.id.content, ArticleListFragment.newInstance(getTagId()));
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

    private String getTagId() {
        return getIntent().getStringExtra(KEY_TAG_ID);
    }

    private String getTagName() {
        return getIntent().getStringExtra(KEY_TAG_NAME);
    }

}
