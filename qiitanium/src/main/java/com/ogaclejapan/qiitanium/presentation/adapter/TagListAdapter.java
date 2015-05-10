package com.ogaclejapan.qiitanium.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.view.TagListItemView;
import com.ogaclejapan.qiitanium.presentation.viewmodel.TagViewModel;
import com.ogaclejapan.qiitanium.util.Objects;
import com.ogaclejapan.rx.binding.RxListAdapter;

public class TagListAdapter extends RxListAdapter<TagViewModel> {

  protected TagListAdapter(final Context context) {
    super(context);
  }

  public static TagListAdapter create(Context context) {
    return new TagListAdapter(context);
  }

  @Override
  protected View newView(final LayoutInflater inflater, final int position,
      final ViewGroup parent) {
    return inflater.inflate(R.layout.list_item_tag, parent, false);
  }

  @Override
  protected void bindView(final TagViewModel item, final int position, final View v) {
    Objects.<TagListItemView>cast(v).bindTo(item);
  }

}
