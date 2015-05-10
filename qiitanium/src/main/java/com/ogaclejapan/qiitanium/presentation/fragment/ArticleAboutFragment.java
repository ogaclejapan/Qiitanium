package com.ogaclejapan.qiitanium.presentation.fragment;

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

import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class ArticleAboutFragment extends AppFragment
    implements View.OnClickListener, RxReadOnlyList.OnDataSetChangeListener,
    AbsListView.OnScrollListener {

  @SuppressWarnings("unused")
  public static final String TAG = ArticleAboutFragment.class.getSimpleName();
  private static final String KEY_ARTICLE_ID = TAG + ":article_id";

  private Rx<ImageView> authorImage;
  private Rx<TextView> authorText;
  private Rx<TextProgressBar> progressBar;
  private Rx<View> emptyText;
  private RxReadOnlyList<TagViewModel> tagList;
  private CommentListAdapter listAdapter;
  private ListView listView;
  private ArticleDetailViewModel viewModel;
  private CommentListViewModel commentListViewModel;
  private LoadMoreHelper loadMoreHelper;
  private PicassoHelper picassoHelper;
  private Transformation roundedTransformation;
  private ViewModelHolder<ArticleDetailViewModel> viewModelHolder = null;

  public static ArticleAboutFragment newInstance(String articleId) {
    return new Bundler()
        .putString(KEY_ARTICLE_ID, articleId)
        .into(new ArticleAboutFragment());
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof ViewModelHolder) {
      viewModelHolder = (ViewModelHolder<ArticleDetailViewModel>) activity;
    }
    picassoHelper = PicassoHelper.create(activity);
    roundedTransformation = picassoHelper.roundedTransformation().oval(true).build();
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

    authorImage = RxView.findById(view, R.id.fragment_article_about_author_image);
    authorText = RxView.findById(view, R.id.fragment_article_about_author_text);
    progressBar = RxView.findById(view, R.id.progress);

    listView = ViewUtils.findById(view, R.id.list);

    loadMoreHelper = LoadMoreHelper.with(listView);
    listAdapter = CommentListAdapter.create(getContext());

    emptyText = RxView.of(ViewUtils.inflate(listView, R.layout.list_item_comment_footer));
    listView.addFooterView(emptyText.get());

    tagList = RxList.create();
    tagList.setOnDataSetChangeListener(this);

    listView.setAdapter(listAdapter);

    listView.setOnScrollListener(this);
    view.findViewById(R.id.fragment_article_about_author).setOnClickListener(this);

  }

  @Override
  protected Subscription onBind() {
    viewModel = viewModelHolder.get();
    commentListViewModel = CommentListViewModel.create(getContext());
    commentListViewModel.setArticleId(getArticleId());

    return Subscriptions.from(
        commentListViewModel,
        listAdapter.bind(commentListViewModel.items()),
        emptyText.bind(commentListViewModel.isEmpty(), RxActions.setVisibility()),
        progressBar.bind(commentListViewModel.isLoading(), RxActions.setVisibility()),
        authorText.bind(viewModel.authorName(), RxActions.setText()),
        authorImage.bind(viewModel.authorThumbnailUrl(), loadThumbnailAction()),
        tagList.bind(viewModel.tags())
    );
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    commentListViewModel.loadMore();
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.fragment_article_about_author:
        openBrowser(viewModel.authorUrl().get());
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

    if (ViewUtils.isVisible(progressBar.get())) {
      return;
    }

    if (loadMoreHelper.onNext(firstVisibleItem + visibleItemCount, totalItemCount)) {
      commentListViewModel.loadMore();
    }

  }

  @Override
  public void onDataSetChanged(RxReadOnlyList.Event event) {
    LayoutInflater inflater = LayoutInflater.from(getContext());

    for (int i = 0, size = tagList.size(); i < size; i++) {
      TagListItemView itemView = Objects.cast(
          inflater.inflate(R.layout.list_item_tag, listView, false));
      itemView.bindTo(tagList.get(i));
      listView.addHeaderView(itemView);
    }

    View commentHeader = inflater.inflate(R.layout.list_item_comment_header, listView, false);
    TypefaceHelper.typeface(commentHeader);
    listView.addHeaderView(commentHeader, null, false);
  }

  protected RxAction<ImageView, String> loadThumbnailAction() {
    return new RxAction<ImageView, String>() {
      @Override
      public void call(final ImageView imageView, final String url) {
        picassoHelper
            .load(url)
            .placeholder(R.drawable.ic_person_outline_white_24dp)
            .error(R.drawable.ic_person_outline_white_24dp)
            .resizeDimen(R.dimen.thumbnail_medium, R.dimen.thumbnail_medium)
            .transform(roundedTransformation)
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
