package com.ogaclejapan.qiitanium.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.view.View;

public final class AnimatorUtils {

    public static final String ALPHA = "alpha";

    public static final String ROTATION = "rotation";

    public static final String ROTATION_X = "rotationX";

    public static final String ROTATION_Y = "rotationY";

    public static final String SCALE_X = "scaleX";

    public static final String SCALE_Y = "scaleY";

    public static final String TRANSLATION_X = "translationX";

    public static final String TRANSLATION_Y = "translationY";

    public static final String TRANSLATION_Z = "translationZ";

    public static void reset(View target) {
        target.setRotationX(0f);
        target.setRotationY(0f);
        target.setRotation(0f);
        target.setScaleX(1f);
        target.setScaleY(1f);
        target.setAlpha(1f);
        target.setTranslationX(0f);
        target.setTranslationY(0f);
    }

    public static Animator together(Animator... animators) {
        AnimatorSet anim = new AnimatorSet();
        anim.playTogether(animators);
        return anim;
    }

    public static Animator together(TimeInterpolator interpolator, Animator... animators) {
        AnimatorSet anim = new AnimatorSet();
        anim.setInterpolator(interpolator);
        anim.playTogether(animators);
        return anim;
    }

    public static Animator sequentially(Animator... animators) {
        AnimatorSet anim = new AnimatorSet();
        anim.playSequentially(animators);
        return anim;
    }

    public static Animator sequentially(TimeInterpolator interpolator, Animator... animators) {
        AnimatorSet anim = new AnimatorSet();
        anim.setInterpolator(interpolator);
        anim.playSequentially(animators);
        return anim;
    }

    public static Animator fadeIn(View target) {
        return alpha(target, 0f, 1f);
    }

    public static Animator fadeIn(View target, TimeInterpolator interpolator) {
        Animator anim = fadeIn(target);
        anim.setInterpolator(interpolator);
        return anim;
    }

    public static Animator fadeOut(View target) {
        return alpha(target, 1f, 0f);
    }

    public static Animator fadeOut(View target, TimeInterpolator interpolator) {
        Animator anim = fadeOut(target);
        anim.setInterpolator(interpolator);
        return anim;
    }

    public static Animator scaleIn(View target) {
        return scale(target, 0f, 1f);
    }

    public static Animator scaleIn(View target, TimeInterpolator interpolator) {
        Animator anim = scaleIn(target);
        anim.setInterpolator(interpolator);
        return anim;
    }

    public static Animator scaleOut(View target) {
        return scale(target, 1f, 0f);
    }

    public static Animator scaleOut(View target, TimeInterpolator interpolator) {
        Animator anim = scaleOut(target);
        anim.setInterpolator(interpolator);
        return anim;
    }

    public static Animator alpha(View target, float... values) {
        return ObjectAnimator.ofPropertyValuesHolder(target, ofAlpha(values));
    }

    public static Animator alpha(View target, TimeInterpolator interpolator, float... values) {
        Animator anim = alpha(target, values);
        anim.setInterpolator(interpolator);
        return anim;
    }

    public static Animator rotation(View target, float... values) {
        return ObjectAnimator.ofPropertyValuesHolder(target, ofRotation(values));
    }

    public static Animator rotation(View target, TimeInterpolator interpolator, float... values) {
        Animator anim = rotation(target, values);
        anim.setInterpolator(interpolator);
        return anim;
    }

    public static Animator rotationX(View target, float... values) {
        return ObjectAnimator.ofPropertyValuesHolder(target, ofRotationX(values));
    }

    public static Animator rotationX(View target, TimeInterpolator interpolator, float... values) {
        Animator anim = rotationX(target, values);
        anim.setInterpolator(interpolator);
        return anim;
    }

    public static Animator rotationY(View target, float... values) {
        return ObjectAnimator.ofPropertyValuesHolder(target, ofRotationY(values));
    }

    public static Animator rotationY(View target, TimeInterpolator interpolator, float... values) {
        Animator anim = rotationY(target, values);
        anim.setInterpolator(interpolator);
        return anim;
    }

    public static Animator translationX(View target, float... values) {
        return ObjectAnimator.ofPropertyValuesHolder(target, ofTranslationX(values));
    }

    public static Animator translationX(View target, TimeInterpolator interpolator, float... values) {
        Animator anim = translationX(target, values);
        anim.setInterpolator(interpolator);
        return anim;
    }

    public static Animator translationY(View target, float... values) {
        return ObjectAnimator.ofPropertyValuesHolder(target, ofTranslationY(values));
    }

    public static Animator translationY(View target, TimeInterpolator interpolator, float... values) {
        Animator anim = translationY(target, values);
        anim.setInterpolator(interpolator);
        return anim;
    }

    public static Animator translationZ(View target, float... values) {
        return ObjectAnimator.ofPropertyValuesHolder(target, ofTranslationZ(values));
    }

    public static Animator translationZ(View target, TimeInterpolator interpolator, float... values) {
        Animator anim = translationZ(target, values);
        anim.setInterpolator(interpolator);
        return anim;
    }

    public static Animator scale(View target, float... values) {
        return ObjectAnimator.ofPropertyValuesHolder(target, ofScaleX(values), ofScaleY(values));
    }

    public static Animator scale(View target, TimeInterpolator interpolator, float... values) {
        Animator anim = scale(target, values);
        anim.setInterpolator(interpolator);
        return anim;
    }

    public static Animator of(View target, PropertyValuesHolder... values) {
        return ObjectAnimator.ofPropertyValuesHolder(target, values);
    }

    public static Animator of(View target, TimeInterpolator interpolator, PropertyValuesHolder... values) {
        Animator anim = of(target, values);
        anim.setInterpolator(interpolator);
        return anim;
    }

    public static PropertyValuesHolder ofAlpha(float... values) {
        return PropertyValuesHolder.ofFloat(ALPHA, values);
    }

    public static PropertyValuesHolder ofRotation(float... values) {
        return PropertyValuesHolder.ofFloat(ROTATION, values);
    }

    public static PropertyValuesHolder ofRotationX(float... values) {
        return PropertyValuesHolder.ofFloat(ROTATION_X, values);
    }

    public static PropertyValuesHolder ofRotationY(float... values) {
        return PropertyValuesHolder.ofFloat(ROTATION_Y, values);
    }

    public static PropertyValuesHolder ofTranslationX(float... values) {
        return PropertyValuesHolder.ofFloat(TRANSLATION_X, values);
    }

    public static PropertyValuesHolder ofTranslationY(float... values) {
        return PropertyValuesHolder.ofFloat(TRANSLATION_Y, values);
    }

    public static PropertyValuesHolder ofTranslationZ(float... values) {
        return PropertyValuesHolder.ofFloat(TRANSLATION_Z, values);
    }

    public static PropertyValuesHolder ofScaleX(float... values) {
        return PropertyValuesHolder.ofFloat(SCALE_X, values);
    }

    public static PropertyValuesHolder ofScaleY(float... values) {
        return PropertyValuesHolder.ofFloat(SCALE_Y, values);
    }

    private AnimatorUtils() {
        //No instances.
    }
}
