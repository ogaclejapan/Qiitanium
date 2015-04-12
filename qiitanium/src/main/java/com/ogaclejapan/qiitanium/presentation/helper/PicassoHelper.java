package com.ogaclejapan.qiitanium.presentation.helper;


import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.ogaclejapan.qiitanium.Qiitanium;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import java.io.File;

import javax.inject.Inject;


public class PicassoHelper {

    private final Picasso mPicasso;

    @Inject
    public PicassoHelper(Picasso picasso) {
        mPicasso = picasso;
    }

    public static PicassoHelper create(final Context context) {
        final Picasso picasso = Qiitanium.appComponent(context).getPicasso();
        return new PicassoHelper(picasso);
    }

    public RequestCreator load(final Uri uri) {
        return mPicasso.load(uri);
    }

    public RequestCreator load(final File file) {
        return mPicasso.load(file);
    }

    public RequestCreator load(final int resourceId) {
        return mPicasso.load(resourceId);
    }

    public RequestCreator load(final String path) {
        return mPicasso.load(path);
    }

    public void cancelRequest(final ImageView view) {
        mPicasso.cancelRequest(view);
    }

    public void cancelRequest(final Target target) {
        mPicasso.cancelRequest(target);
    }

    public RoundedTransformationBuilder roundedTransformation() {
        return new RoundedTransformationBuilder();
    }

}
