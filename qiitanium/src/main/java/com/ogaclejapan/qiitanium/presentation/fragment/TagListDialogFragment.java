package com.ogaclejapan.qiitanium.presentation.fragment;

import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.activity.TagActivity;
import com.ogaclejapan.qiitanium.presentation.adapter.TagListAdapter;
import com.ogaclejapan.qiitanium.presentation.viewmodel.ArticleDetailViewModel;
import com.ogaclejapan.qiitanium.presentation.viewmodel.TagViewModel;
import com.ogaclejapan.qiitanium.util.Bundler;
import com.ogaclejapan.qiitanium.util.ViewUtils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class TagListDialogFragment extends AppDialogFragment
        implements AbsListView.OnItemClickListener {

    public static final String TAG = TagListDialogFragment.class.getSimpleName();

    private static final String KEY_ARTICLE_ID = TAG + ":article_id";

    public static TagListDialogFragment newInstance(String articleId) {
        return new Bundler()
                .putString(KEY_ARTICLE_ID, articleId)
                .into(new TagListDialogFragment());
    }

    private TagListAdapter mListAdapter;

    private ArticleDetailViewModel mViewModel;

    public TagListDialogFragment() {
        super(TAG);
    }

    private String getArticleId() {
        return getArguments().getString(KEY_ARTICLE_ID);
    }

    @Override
    protected View onSetupView(final LayoutInflater inflater, final Bundle savedInstanceState) {
        mListAdapter = TagListAdapter.create(getContext());
        mViewModel = ArticleDetailViewModel.create(getContext());

        final View view = inflater.inflate(R.layout.fragment_article_tag, null, false);

        final ListView listView = ViewUtils.findById(view, R.id.list);
        listView.setEmptyView(view.findViewById(R.id.empty_container));
        listView.setOnItemClickListener(this);

        final AnimationAdapter animAdapter = new ScaleInAnimationAdapter(mListAdapter);
        animAdapter.setAbsListView(listView);
        listView.setAdapter(animAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.loadArticle(getArticleId());
    }

    @Override
    protected Subscription onBind() {
        return Subscriptions.from(
                mViewModel,
                mListAdapter.bind(mViewModel.tags())
        );
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position,
            final long id) {
        final TagViewModel item = mListAdapter.getItem(position);
        startActivity(TagActivity.intentOf(getContext(), item.id(), item.name().get()));
    }

}
