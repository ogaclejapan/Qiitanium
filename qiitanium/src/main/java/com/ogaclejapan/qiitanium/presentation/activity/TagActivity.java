package com.ogaclejapan.qiitanium.presentation.activity;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.fragment.ArticleListFragment;
import com.ogaclejapan.qiitanium.presentation.helper.MaterialMenuBar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toolbar;

public class TagActivity extends AppActivity {

    public static final String TAG = TagActivity.class.getSimpleName();

    private static final String KEY_TAG_ID = TAG + ":tag_id";
    private static final String KEY_TAG_NAME = TAG + ":tag_name";

    public static Intent intentOf(Context context, String tagId, String tagName) {
        Intent intent = new Intent(context, TagActivity.class);
        intent.putExtra(KEY_TAG_ID, tagId);
        intent.putExtra(KEY_TAG_NAME, tagName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    private static String getTagId(Intent intent) {
        return intent.getStringExtra(KEY_TAG_ID);
    }

    private static String getTagName(Intent intent) {
        return intent.getStringExtra(KEY_TAG_NAME);
    }

    private MaterialMenuBar mMaterialMenuBar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        mMaterialMenuBar = MaterialMenuBar.with(this);
        mMaterialMenuBar.setState(MaterialMenuBar.ICON_ARROW);
        final Toolbar toolbar = mMaterialMenuBar.getToolbar();
        toolbar.setTitle(getTagName());
        setActionBar(toolbar);

        if (savedInstanceState == null) {
            setFragment(R.id.main_container, ArticleListFragment.newInstance(getTagId()));
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMaterialMenuBar.syncState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull final Bundle outState) {
        mMaterialMenuBar.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    private String getTagId() {
        return getTagId(getIntent());
    }

    private String getTagName() {
        return getTagName(getIntent());
    }

}
