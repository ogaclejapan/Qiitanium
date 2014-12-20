package com.ogaclejapan.qiitanium.presentation.helper;

import com.balysv.materialmenu.MaterialMenuBase;
import com.balysv.materialmenu.MaterialMenuDrawable;
import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.util.ContextUtils;
import com.ogaclejapan.qiitanium.util.ViewUtils;

import android.app.Activity;
import android.view.View;
import android.widget.Toolbar;

public class MaterialMenuBar extends MaterialMenuBase {

    public static final MaterialMenuDrawable.Stroke STROKE_REGULAR
            = MaterialMenuDrawable.Stroke.REGULAR;

    public static final MaterialMenuDrawable.Stroke STROKE_THIN
            = MaterialMenuDrawable.Stroke.THIN;

    public static final MaterialMenuDrawable.Stroke STROKE_EXTRA_THIN
            = MaterialMenuDrawable.Stroke.EXTRA_THIN;

    public static final MaterialMenuDrawable.IconState ICON_BURGER
            = MaterialMenuDrawable.IconState.BURGER;

    public static final MaterialMenuDrawable.IconState ICON_CLOSE
            = MaterialMenuDrawable.IconState.X;

    public static final MaterialMenuDrawable.IconState ICON_ARROW
            = MaterialMenuDrawable.IconState.ARROW;

    public static final MaterialMenuDrawable.IconState ICON_CHECK
            = MaterialMenuDrawable.IconState.CHECK;

    private Toolbar mToolbar;

    protected MaterialMenuBar(Activity activity) {
        this(activity, ContextUtils.getColor(activity, R.color.material_menu_icon));
    }

    protected MaterialMenuBar(Activity activity, int color) {
        super(activity, color, MaterialMenuDrawable.Stroke.REGULAR);
    }

    public static MaterialMenuBar with(Activity activity) {
        return new MaterialMenuBar(activity);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void setActionBarSettings(final Activity activity) {
        mToolbar = ViewUtils.findById(activity, R.id.toolbar);
        if (mToolbar == null) {
            throw new IllegalStateException("Not found Toolbar.");
        }
        mToolbar.setNavigationIcon(getDrawable());
    }

    @Override
    protected View getActionBarHomeView(final Activity activity) {
        return null;
    }

    @Override
    protected View getActionBarUpView(final Activity activity) {
        return null;
    }

    @Override
    protected boolean providesActionBar() {
        return false;
    }

}
