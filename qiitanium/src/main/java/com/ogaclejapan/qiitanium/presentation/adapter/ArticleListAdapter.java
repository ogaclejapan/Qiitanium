package com.ogaclejapan.qiitanium.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.view.ArticleListItemView;
import com.ogaclejapan.qiitanium.presentation.viewmodel.ArticleViewModel;
import com.ogaclejapan.qiitanium.util.Objects;
import com.ogaclejapan.rx.binding.RxListAdapter;

public class ArticleListAdapter extends RxListAdapter<ArticleViewModel> {

  protected ArticleListAdapter(final Context context) {
    super(context);
  }

  public static ArticleListAdapter create(final Context context) {
    return new ArticleListAdapter(context);
  }

  @Override
  protected View newView(final LayoutInflater inflater, final int position,
      final ViewGroup parent) {
    return inflater.inflate(R.layout.list_item_article, parent, false);
  }

  @Override
  protected void bindView(final ArticleViewModel item, final int position,
      final View v) {
    Objects.<ArticleListItemView>cast(v).bindTo(item);
  }

}
