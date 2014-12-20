package com.ogaclejapan.qiitanium.presentation.view;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.presentation.helper.FragmentPager;
import com.ogaclejapan.qiitanium.util.ContextUtils;
import com.ogaclejapan.qiitanium.util.Objects;
import com.ogaclejapan.rx.binding.Rx;
import com.ogaclejapan.rx.binding.RxAction;
import com.ogaclejapan.rx.binding.RxProperty;
import com.ogaclejapan.rx.binding.RxView;

import android.content.Context;
import android.graphics.Outline;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewOutlineProvider;
import android.widget.ImageButton;

import rx.Subscription;
import rx.subscriptions.Subscriptions;
import timber.log.Timber;

public class DrawerMenuView extends AppView<FragmentPager.Adapter> implements View.OnClickListener {

    private static final RxAction<View, Integer> SELECT_STATE_CHANGE_ACTION =
            new RxAction<View, Integer>() {
                @Override
                public void call(final View view, final Integer id) {
                    view.setSelected(view.getId() == id);
                }
            };

    private static final RxAction<FragmentPager.Adapter, Integer> SELECT_CHANGE_ACTION =
            new RxAction<FragmentPager.Adapter, Integer>() {
                @Override
                public void call(final FragmentPager.Adapter pagerAdapter, final Integer id) {
                    final int position = pagerAdapter.getIndexOf(id);
                    final ViewPager viewPager = pagerAdapter.getViewPager();
                    if (position != viewPager.getCurrentItem()) {
                        viewPager.setCurrentItem(position, false);
                    }
                }
            };

    private final RxProperty<Integer> mCurrentId = RxProperty.of(R.id.drawer_menu_feed);

    private Rx<ImageButton> mDrawerMenuFeed;
    private Rx<ImageButton> mDrawerMenuTag;
    private Rx<ImageButton> mDrawerMenuMyPage;
    private Rx<ImageButton> mDrawerMenuSettings;

    private OnClickListener mOnClickListener = null;

    public DrawerMenuView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setOnClickListener(final OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    protected void onViewCreated(final View view) {
        mDrawerMenuFeed = RxView.findById(view, R.id.drawer_menu_feed);
        mDrawerMenuTag = RxView.findById(view, R.id.drawer_menu_tag);
        mDrawerMenuMyPage = RxView.findById(view, R.id.drawer_menu_mypage);
        mDrawerMenuSettings = RxView.findById(view, R.id.drawer_menu_settings);
        mDrawerMenuFeed.get().setOnClickListener(this);
        mDrawerMenuTag.get().setOnClickListener(this);
        mDrawerMenuMyPage.get().setOnClickListener(this);
        mDrawerMenuSettings.get().setOnClickListener(this);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), mCurrentId.get());
    }

    @Override
    protected void onRestoreInstanceState(final Parcelable state) {
        SavedState inState = Objects.cast(state);
        super.onRestoreInstanceState(inState.getSuperState());
        mCurrentId.set(inState.getId());
    }

    @Override
    protected Subscription onBind(final FragmentPager.Adapter adapter) {
        return Subscriptions.from(
                mDrawerMenuFeed.bind(mCurrentId, SELECT_STATE_CHANGE_ACTION),
                mDrawerMenuTag.bind(mCurrentId, SELECT_STATE_CHANGE_ACTION),
                mDrawerMenuMyPage.bind(mCurrentId, SELECT_STATE_CHANGE_ACTION),
                mDrawerMenuSettings.bind(mCurrentId, SELECT_STATE_CHANGE_ACTION),
                RxView.of(adapter).bind(mCurrentId, SELECT_CHANGE_ACTION)
        );
    }

    @Override
    public void onClick(final View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.drawer_menu_feed:
            case R.id.drawer_menu_tag:
            case R.id.drawer_menu_mypage:
            case R.id.drawer_menu_settings:
                mCurrentId.set(id);
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(v);
                }
                break;
            default:
                Timber.d("Unknown id=%d", id);
        }
    }

    private static class SavedState extends BaseSavedState {

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(final Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(final int size) {
                return new SavedState[size];
            }
        };

        private int mId;

        private SavedState(final Parcelable superState, int id) {
            super(superState);
            mId = id;
        }

        private SavedState(final Parcel in) {
            super(in);
            mId = in.readInt();
        }

        public int getId() {
            return mId;
        }

        @Override
        public void writeToParcel(@NonNull final Parcel out, final int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(mId);
        }

    }

}
