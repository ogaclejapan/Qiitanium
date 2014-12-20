package com.ogaclejapan.qiitanium.presentation.helper;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.util.BitmapUtils;
import com.ogaclejapan.qiitanium.util.ContextUtils;
import com.ogaclejapan.qiitanium.util.ViewUtils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MaterialMenuDrawerToggle extends MaterialMenuBar
        implements DrawerLayout.DrawerListener {

    private static final int HOME = android.R.id.home;

    private final DrawerLayout mDrawerLayout;

    private MaterialMenuDrawable.IconState mMenuState = ICON_CLOSE;

    protected MaterialMenuDrawerToggle(final Activity activity) {
        super(activity);
        mDrawerLayout = ViewUtils.findById(activity, R.id.drawer_layout);
        if (mDrawerLayout == null) {
            throw new IllegalStateException("Not found DrawerLayout.");
        }
        mDrawerLayout.setDrawerListener(this);
        mDrawerLayout.setScrimColor(ContextUtils.getColor(activity, R.color.drawer_menu_scrim));
    }

    public static MaterialMenuDrawerToggle with(final Activity activity) {
        return with(activity, true);
    }

    public static MaterialMenuDrawerToggle with(final Activity activity, boolean isTransparent) {
        if (isTransparent) {
            return new MaterialMenuDrawerToggle.BlurMode(activity);
        } else {
            return new MaterialMenuDrawerToggle(activity);
        }
    }

    public void syncState() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            setMenuState(ICON_CLOSE);
        } else {
            setMenuState(ICON_BURGER);
        }
    }

    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item != null && item.getItemId() == HOME) {
            if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
                animatePressedMenuState(ICON_BURGER);
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                animatePressedMenuState(ICON_CLOSE);
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return false;
    }

    public void closeDrawers() {
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void onDrawerOpened(final View drawerView) {
        animateMenuState(ICON_CLOSE);
    }

    @Override
    public void onDrawerClosed(final View drawerView) {
        animateMenuState(ICON_BURGER);
    }

    @Override
    public void onDrawerSlide(final View drawerView, final float slideOffset) {
        //Do nothing.
    }

    @Override
    public void onDrawerStateChanged(final int newState) {
        //Do nothing.
    }

    private void setMenuState(final MaterialMenuDrawable.IconState state) {
        setState(state);
        mMenuState = state;
    }

    private void animateMenuState(final MaterialMenuDrawable.IconState state) {
        if (mMenuState == state) {
            return;
        }
        if (getDrawable().isRunning()) {
            getDrawable().stop();
        }
        animateState(state);
        mMenuState = state;
    }

    private void animatePressedMenuState(final MaterialMenuDrawable.IconState state) {
        if (mMenuState == state) {
            return;
        }
        if (getDrawable().isRunning()) {
            getDrawable().stop();
        }
        animatePressedState(state);
        mMenuState = state;
    }

    private static class BlurMode extends MaterialMenuDrawerToggle {

        private final ViewGroup mContentLayout;

        private final ImageView mBlurImage;

        private BlurTask mBlurTask = null;

        private BlurMode(Activity activity) {
            super(activity);
            mContentLayout = ViewUtils.findById(activity, R.id.main_container);
            mBlurImage = ViewUtils.findById(activity, R.id.main_container_image);
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            super.onDrawerSlide(drawerView, slideOffset);
            if (slideOffset > 0.0f) {
                setBlurAlpha(slideOffset);
            } else {
                clearBlurImage();
            }
        }

        private void setBlurAlpha(float slideOffset) {
            if (mBlurTask == null) {
                mBlurTask = new BlurTask(mBlurImage);
                mBlurTask.execute(BitmapUtils.capture(mContentLayout));
            }
            mBlurImage.setAlpha(slideOffset);
        }

        private void clearBlurImage() {
            if (mBlurTask != null && !mBlurTask.isDone() && mBlurTask.isCancelled()) {
                mBlurTask.cancel(true);
            }
            mBlurTask = null;
            mBlurImage.setVisibility(View.GONE);
            mBlurImage.setImageBitmap(null);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            clearBlurImage();
        }

    }

    private static class BlurTask extends AsyncTask<Bitmap, Void, Bitmap> {

        private final ImageView mBlurImage;

        private boolean mIsDone = false;

        private BlurTask(ImageView image) {
            mBlurImage = image;
        }

        @Override
        protected void onPreExecute() {
            mBlurImage.setImageBitmap(null);
        }

        @Override
        protected Bitmap doInBackground(Bitmap... params) {
            if (isCancelled()) {
                return null;
            }
            final Bitmap source = params[0];
            return BitmapUtils.blur(mBlurImage.getContext(), source);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                return;
            }
            mIsDone = true;
            mBlurImage.setImageBitmap(bitmap);
            mBlurImage.setVisibility(View.VISIBLE);
        }

        boolean isDone() {
            return mIsDone;
        }

    }

}
