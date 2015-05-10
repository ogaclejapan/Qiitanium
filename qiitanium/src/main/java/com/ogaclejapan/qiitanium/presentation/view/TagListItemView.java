package com.ogaclejapan.qiitanium.presentation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.norbsoft.typefacehelper.TypefaceHelper;
import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.activity.TagActivity;
import com.ogaclejapan.qiitanium.presentation.viewmodel.TagViewModel;
import com.ogaclejapan.rx.binding.Rx;
import com.ogaclejapan.rx.binding.RxActions;
import com.ogaclejapan.rx.binding.RxView;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class TagListItemView extends AppView<TagViewModel>
    implements View.OnClickListener {

  private Rx<TextView> nameText;

  public TagListItemView(final Context context, final AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onViewCreated(final View view) {
    nameText = RxView.findById(view, R.id.list_item_tag_name);

    // Apply custom font
    TypefaceHelper.typeface(nameText.get());

    setOnClickListener(this);
  }

  @Override
  protected Subscription onBind(final TagViewModel item) {
    return Subscriptions.from(
        nameText.bind(item.name(), RxActions.setText())
    );
  }

  @Override
  public void onClick(View v) {
    TagViewModel item = getItem();
    TagActivity.startActivity(getContext(), item.id(), item.name().get());
  }

}
