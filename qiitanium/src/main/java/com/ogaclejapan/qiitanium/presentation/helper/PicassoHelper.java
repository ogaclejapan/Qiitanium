package com.ogaclejapan.qiitanium.presentation.helper;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.ogaclejapan.qiitanium.Qiitanium;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.io.File;

import javax.inject.Inject;

public class PicassoHelper {

  private final Picasso picasso;

  @Inject
  public PicassoHelper(Picasso picasso) {
    this.picasso = picasso;
  }

  public static PicassoHelper create(final Context context) {
    final Picasso picasso = Qiitanium.appComponent(context).getPicasso();
    return new PicassoHelper(picasso);
  }

  public RequestCreator load(final Uri uri) {
    return picasso.load(uri);
  }

  public RequestCreator load(final File file) {
    return picasso.load(file);
  }

  public RequestCreator load(final int resourceId) {
    return picasso.load(resourceId);
  }

  public RequestCreator load(final String path) {
    return picasso.load(path);
  }

  public void cancelRequest(final ImageView view) {
    picasso.cancelRequest(view);
  }

  public void cancelRequest(final Target target) {
    picasso.cancelRequest(target);
  }

  public RoundedTransformationBuilder roundedTransformation() {
    return new RoundedTransformationBuilder();
  }

}
