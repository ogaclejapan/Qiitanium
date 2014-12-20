package com.ogaclejapan.qiitanium.presentation.activity;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.fragment.ArticleDetailFragment;
import com.ogaclejapan.qiitanium.presentation.helper.MaterialMenuBar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toolbar;

public class ArticleDetailActivity extends AppActivity {

    private static final String TAG = ArticleDetailActivity.class.getSimpleName();

    private static final String KEY_ARTICLE_ID = TAG + ":article_id";
    private static final String KEY_AUTHOR_NAME = TAG + ":author_name";

    public static Intent intentOf(Context context, String articleId, String authorName) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(KEY_ARTICLE_ID, articleId);
        intent.putExtra(KEY_AUTHOR_NAME, authorName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    private static String getArticleId(Intent intent) {
        return intent.getStringExtra(KEY_ARTICLE_ID);
    }

    private static String getAuthorName(Intent intent) {
        return intent.getStringExtra(KEY_AUTHOR_NAME);
    }

    private MaterialMenuBar mMaterialMenuBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        mMaterialMenuBar = MaterialMenuBar.with(this);
        mMaterialMenuBar.setState(MaterialMenuBar.ICON_ARROW);
        final Toolbar toolbar = mMaterialMenuBar.getToolbar();
        toolbar.setTitle(getAuthorName());
        setActionBar(toolbar);

        if (savedInstanceState == null) {
            setFragment(R.id.main_container, ArticleDetailFragment.newInstance(getArticleId()));
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

    protected String getArticleId() {
        return getArticleId(getIntent());
    }

    protected String getAuthorName() {
        return getAuthorName(getIntent());
    }

}
