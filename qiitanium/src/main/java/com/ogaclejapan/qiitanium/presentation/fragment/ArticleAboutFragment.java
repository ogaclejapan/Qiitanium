package com.ogaclejapan.qiitanium.presentation.fragment;

import com.norbsoft.typefacehelper.TypefaceHelper;
import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.adapter.CommentListAdapter;
import com.ogaclejapan.qiitanium.presentation.helper.LoadMoreHelper;
import com.ogaclejapan.qiitanium.presentation.helper.PicassoHelper;
import com.ogaclejapan.qiitanium.presentation.listener.ViewModelHolder;
import com.ogaclejapan.qiitanium.presentation.view.TagListItemView;
import com.ogaclejapan.qiitanium.presentation.viewmodel.ArticleDetailViewModel;
import com.ogaclejapan.qiitanium.presentation.viewmodel.CommentListViewModel;
import com.ogaclejapan.qiitanium.presentation.viewmodel.TagViewModel;
import com.ogaclejapan.qiitanium.presentation.widget.TextProgressBar;
import com.ogaclejapan.qiitanium.util.IntentUtils;
import com.ogaclejapan.qiitanium.util.Objects;
import com.ogaclejapan.qiitanium.util.ViewUtils;
import com.ogaclejapan.rx.binding.Rx;
import com.ogaclejapan.rx.binding.RxAction;
import com.ogaclejapan.rx.binding.RxActions;
import com.ogaclejapan.rx.binding.RxList;
import com.ogaclejapan.rx.binding.RxReadOnlyList;
import com.ogaclejapan.rx.binding.RxView;
import com.ogaclejapan.smarttablayout.utils.v13.Bundler;
import com.squareup.picasso.Transformation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class ArticleAboutFragment extends AppFragment
        implements View.OnClickListener, RxReadOnlyList.OnDataSetChangeListener,
        AbsListView.OnScrollListener {

    @SuppressWarnings("unused")
    public static final String TAG = ArticleAboutFragment.class.getSimpleName();
    private static final String KEY_ARTICLE_ID = TAG + ":article_id";

    public static ArticleAboutFragment newInstance(String articleId) {
        return new Bundler()
                .putString(KEY_ARTICLE_ID, articleId)
                .into(new ArticleAboutFragment());
    }

    private Rx<ImageView> mAuthorImage;
    private Rx<TextView> mAuthorText;
    private Rx<TextProgressBar> mProgressBar;
    private Rx<View> mEmptyText;

    private RxReadOnlyList<TagViewModel> mTagList;

    private CommentListAdapter mListAdapter;
    private ListView mListView;

    private ArticleDetailViewModel mViewModel;
    private CommentListViewModel mCommentListViewModel;

    private LoadMoreHelper mLoadMoreHelper;

    private PicassoHelper mPicassoHelper;
    private Transformation mRoundedTransformation;

    private ViewModelHolder<ArticleDetailViewModel> mViewModelHolder = null;

    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ViewModelHolder) {
            mViewModelHolder = (ViewModelHolder<ArticleDetailViewModel>) activity;
        }
        mPicassoHelper = PicassoHelper.create(activity);
        mRoundedTransformation = mPicassoHelper.roundedTransformation().oval(true).build();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article_about, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuthorImage = RxView.findById(view, R.id.fragment_article_about_author_image);
        mAuthorText = RxView.findById(view, R.id.fragment_article_about_author_text);
        mProgressBar = RxView.findById(view, R.id.progress);

        mListView = ViewUtils.findById(view, R.id.list);

        mLoadMoreHelper = LoadMoreHelper.with(mListView);
        mListAdapter = CommentListAdapter.create(getContext());

        mEmptyText = RxView.of(ViewUtils.inflate(mListView, R.layout.list_item_comment_footer));
        mListView.addFooterView(mEmptyText.get());

        mTagList = RxList.create();
        mTagList.setOnDataSetChangeListener(this);

        mListView.setAdapter(mListAdapter);

        mListView.setOnScrollListener(this);
        view.findViewById(R.id.fragment_article_about_author).setOnClickListener(this);

    }

    @Override
    protected Subscription onBind() {
        mViewModel = mViewModelHolder.get();
        mCommentListViewModel = CommentListViewModel.create(getContext());
        mCommentListViewModel.setArticleId(getArticleId());

        return Subscriptions.from(
                mCommentListViewModel,
                mListAdapter.bind(mCommentListViewModel.items()),
                mEmptyText.bind(mCommentListViewModel.isEmpty(), RxActions.setVisibility()),
                mProgressBar.bind(mCommentListViewModel.isLoading(), RxActions.setVisibility()),
                mAuthorText.bind(mViewModel.authorName(), RxActions.setText()),
                mAuthorImage.bind(mViewModel.authorThumbnailUrl(), loadThumbnailAction()),
                mTagList.bind(mViewModel.tags())
        );
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCommentListViewModel.loadMore();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_article_about_author:
                openBrowser(mViewModel.authorUrl().get());
                break;
            default:
                //Do nothing.
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //Do nothing.
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
            int totalItemCount) {

        if (ViewUtils.isVisible(mProgressBar.get())) {
            return;
        }

        if (mLoadMoreHelper.onNext(firstVisibleItem + visibleItemCount, totalItemCount)) {
            mCommentListViewModel.loadMore();
        }

    }

    @Override
    public void onDataSetChanged(RxReadOnlyList.Event event) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        for (int i = 0, size = mTagList.size(); i < size; i++) {
            TagListItemView itemView = Objects.cast(
                    inflater.inflate(R.layout.list_item_tag, mListView, false));
            itemView.bindTo(mTagList.get(i));
            mListView.addHeaderView(itemView);
        }

        View commentHeader = inflater.inflate(R.layout.list_item_comment_header, mListView, false);
        TypefaceHelper.typeface(commentHeader);
        mListView.addHeaderView(commentHeader, null, false);
    }

    protected RxAction<ImageView, String> loadThumbnailAction() {
        return new RxAction<ImageView, String>() {
            @Override
            public void call(final ImageView imageView, final String url) {
                mPicassoHelper
                        .load(url)
                        .placeholder(R.drawable.ic_person_outline_white_24dp)
                        .error(R.drawable.ic_person_outline_white_24dp)
                        .resizeDimen(R.dimen.thumbnail_medium, R.dimen.thumbnail_medium)
                        .transform(mRoundedTransformation)
                        .into(imageView);
            }
        };
    }

    private void openBrowser(String url) {
        IntentUtils.openExternalUrl(getContext(), url);
    }

    private String getArticleId() {
        return getArguments().getString(KEY_ARTICLE_ID);
    }

}
