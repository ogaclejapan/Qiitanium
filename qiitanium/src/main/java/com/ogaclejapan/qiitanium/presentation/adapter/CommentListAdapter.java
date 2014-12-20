package com.ogaclejapan.qiitanium.presentation.adapter;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.view.CommentListItemView;
import com.ogaclejapan.qiitanium.presentation.viewmodel.CommentViewModel;
import com.ogaclejapan.qiitanium.util.Objects;
import com.ogaclejapan.rx.binding.RxListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CommentListAdapter extends RxListAdapter<CommentViewModel> {

    protected CommentListAdapter(final Context context) {
        super(context);
    }

    public static CommentListAdapter create(final Context context) {
        return new CommentListAdapter(context);
    }

    @Override
    protected View newView(final LayoutInflater inflater, final int position,
            final ViewGroup parent) {
        return inflater.inflate(R.layout.list_item_comment, parent, false);
    }

    @Override
    protected void bindView(final CommentViewModel item, final int position, final View v) {
        Objects.<CommentListItemView>cast(v).bindTo(item);
    }

}
