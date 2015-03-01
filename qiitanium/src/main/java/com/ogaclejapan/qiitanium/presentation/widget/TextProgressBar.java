package com.ogaclejapan.qiitanium.presentation.widget;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.util.ResUtils;
import com.ogaclejapan.qiitanium.util.Objects;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class TextProgressBar extends ProgressBar {

    private static final String DEFAULT_TEXT = "Progress...";

    private static final int DEFAULT_TEXT_SIZE = 48;

    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;

    private static final int DEFAULT_DURATION = 1500;

    private final TextProgressDrawable mTextProgressDrawable;

    public TextProgressBar(Context context) {
        this(context, null);
    }

    public TextProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextProgressBar(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);

        setIndeterminate(true);

        TextProgressDrawable.Builder tpd = new TextProgressDrawable.Builder(context);

        if (isInEditMode()) {
            mTextProgressDrawable = tpd.build();
            setIndeterminateDrawable(mTextProgressDrawable);
            return;
        }

        if (attrs != null) {

            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextProgressBar);

            final String text = a.getString(R.styleable.TextProgressBar_progressText);
            if (!TextUtils.isEmpty(text)) {
                tpd.setText(text);
            }

            final float textSize = a
                    .getDimension(R.styleable.TextProgressBar_progressTextSize, Float.NaN);
            if (!Float.isNaN(textSize)) {
                tpd.setTextSize(textSize);
            }

            final int textColor = a.getColor(R.styleable.TextProgressBar_progressTextColor, -1);
            if (textColor != -1) {
                tpd.setTextColor(textColor);
            }

            final int duration = a.getInteger(R.styleable.TextProgressBar_progressDuration, -1);
            if (duration != -1) {
                tpd.setDuration(duration);
            }

            a.recycle();

        }

        mTextProgressDrawable = tpd.build();
        setIndeterminateDrawable(mTextProgressDrawable);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final Drawable d = getIndeterminateDrawable();

        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);

        if (d != null && MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY) {
            w = d.getIntrinsicWidth();
        }

        if (d != null && MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY) {
            h = d.getIntrinsicHeight();
        }

        setMeasuredDimension(w, h);
    }

    public boolean isRunning() {
        return Objects.<TextProgressDrawable>cast(getIndeterminateDrawable()).isRunning();
    }

    public static class TextProgressDrawable extends Drawable
            implements Animatable, ValueAnimator.AnimatorUpdateListener {

        private final int mMaxAlpha;

        private final Rect mFBounds;

        private final ValueAnimator mAlphaAnimator;

        private final int mTextWidth;

        private final int mTextHeight;

        private final String mText;

        private final TextPaint mTextPaint;

        private boolean mIsRunning;

        private TextProgressDrawable(Builder builder) {
            mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            mTextPaint.setStyle(Paint.Style.FILL);
            mTextPaint.setColor(builder.mTextColor);
            mTextPaint.setTextSize(builder.mTextSize);

            Paint.FontMetrics fm = mTextPaint.getFontMetrics();
            mText = builder.mText;
            mTextHeight = Math.round(fm.descent - fm.ascent);
            mTextWidth = Math.round(mTextPaint.measureText(mText, 0, mText.length()));

            mMaxAlpha = mTextPaint.getAlpha();
            mFBounds = new Rect();

            mAlphaAnimator = ValueAnimator.ofFloat(1f, 0f, 1f);
            mAlphaAnimator.setDuration(builder.mDuration);
            mAlphaAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mAlphaAnimator.addUpdateListener(this);
        }


        @Override
        public void start() {
            if (isRunning()) {
                return;
            }
            mIsRunning = true;
            mAlphaAnimator.start();
            invalidateSelf();
        }

        @Override
        public void stop() {
            if (!isRunning()) {
                return;
            }
            mIsRunning = false;
            mAlphaAnimator.cancel();
            invalidateSelf();
        }

        @Override
        public boolean isRunning() {
            return mIsRunning;
        }


        @Override
        public int getIntrinsicWidth() {
            return mTextWidth;
        }

        @Override
        public int getIntrinsicHeight() {
            return mTextHeight;
        }

        @Override
        public void draw(Canvas canvas) {

            if (!isRunning()) {
                return;
            }

            final float w = Math.max(mFBounds.right, mTextWidth);
            final float h = Math.max(mFBounds.bottom, mTextHeight);

            final float x = (w - mTextWidth) / 2f;
            final float y = ((h - mTextHeight) / 2f) - mTextPaint.ascent();

            canvas.drawText(mText, x, y, mTextPaint);
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            mFBounds.set(bounds);
        }

        @Override
        public void setAlpha(int alpha) {
            mTextPaint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            mTextPaint.setColorFilter(cf);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            final float value = (Float) animation.getAnimatedValue();
            setAlpha(Math.round(mMaxAlpha * value));
            invalidateSelf();
        }

        public static class Builder {

            private String mText = DEFAULT_TEXT;

            private float mTextSize = DEFAULT_TEXT_SIZE;

            private int mTextColor = DEFAULT_TEXT_COLOR;

            private int mDuration = DEFAULT_DURATION;

            public Builder(Context context) {
                mTextSize = ResUtils.pxToSp(context, (int) mTextSize);
            }

            public Builder setText(String text) {
                mText = text;
                return this;
            }

            public Builder setTextSize(float size) {
                mTextSize = size;
                return this;
            }

            public Builder setTextColor(int color) {
                mTextColor = color;
                return this;
            }

            public Builder setDuration(int duration) {
                mDuration = duration;
                return this;
            }

            public TextProgressDrawable build() {
                return new TextProgressDrawable(this);
            }

        }

    }

}