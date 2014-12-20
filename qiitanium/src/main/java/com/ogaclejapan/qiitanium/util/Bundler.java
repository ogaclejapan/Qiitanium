package com.ogaclejapan.qiitanium.util;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;

public final class Bundler {

    private final Bundle mBundle;

    public Bundler() {
        this(new Bundle());
    }

    private Bundler(Bundle b) {
        mBundle = b;
    }

    public static Bundler of(Bundle b) {
        return new Bundler(b);
    }

    public Bundler putBoolean(String key, boolean value) {
        mBundle.putBoolean(key, value);
        return this;
    }

    public Bundler putByte(String key, byte value) {
        mBundle.putByte(key, value);
        return this;
    }

    public Bundler putChar(String key, char value) {
        mBundle.putChar(key, value);
        return this;
    }

    public Bundler putShort(String key, short value) {
        mBundle.putShort(key, value);
        return this;
    }

    public Bundler putInt(String key, int value) {
        mBundle.putInt(key, value);
        return this;
    }

    public Bundler putLong(String key, long value) {
        mBundle.putLong(key, value);
        return this;
    }

    public Bundler putFloat(String key, float value) {
        mBundle.putFloat(key, value);
        return this;
    }

    public Bundler putDouble(String key, double value) {
        mBundle.putDouble(key, value);
        return this;
    }

    public Bundler putString(String key, String value) {
        mBundle.putString(key, value);
        return this;
    }

    public Bundler putCharSequence(String key, CharSequence value) {
        mBundle.putCharSequence(key, value);
        return this;
    }

    public Bundler putParcelable(String key, Parcelable value) {
        mBundle.putParcelable(key, value);
        return this;
    }

    public Bundler putParcelableArray(String key, Parcelable[] value) {
        mBundle.putParcelableArray(key, value);
        return this;
    }

    public Bundler putParcelableArrayList(String key,
            ArrayList<? extends Parcelable> value) {
        mBundle.putParcelableArrayList(key, value);
        return this;
    }

    public Bundler putSparseParcelableArray(String key,
            SparseArray<? extends Parcelable> value) {
        mBundle.putSparseParcelableArray(key, value);
        return this;
    }

    public Bundler putIntegerArrayList(String key, ArrayList<Integer> value) {
        mBundle.putIntegerArrayList(key, value);
        return this;
    }

    public Bundler putStringArrayList(String key, ArrayList<String> value) {
        mBundle.putStringArrayList(key, value);
        return this;
    }

    public Bundler putCharSequenceArrayList(String key, ArrayList<CharSequence> value) {
        mBundle.putCharSequenceArrayList(key, value);
        return this;
    }

    public Bundler putSerializable(String key, Serializable value) {
        mBundle.putSerializable(key, value);
        return this;
    }

    public Bundler putBooleanArray(String key, boolean[] value) {
        mBundle.putBooleanArray(key, value);
        return this;
    }

    public Bundler putByteArray(String key, byte[] value) {
        mBundle.putByteArray(key, value);
        return this;
    }

    public Bundler putShortArray(String key, short[] value) {
        mBundle.putShortArray(key, value);
        return this;
    }

    public Bundler putCharArray(String key, char[] value) {
        mBundle.putCharArray(key, value);
        return this;
    }

    public Bundler putIntArray(String key, int[] value) {
        mBundle.putIntArray(key, value);
        return this;
    }

    public Bundler putLongArray(String key, long[] value) {
        mBundle.putLongArray(key, value);
        return this;
    }

    public Bundler putFloatArray(String key, float[] value) {
        mBundle.putFloatArray(key, value);
        return this;
    }

    public Bundler putDoubleArray(String key, double[] value) {
        mBundle.putDoubleArray(key, value);
        return this;
    }

    public Bundler putStringArray(String key, String[] value) {
        mBundle.putStringArray(key, value);
        return this;
    }

    public Bundler putCharSequenceArray(String key, CharSequence[] value) {
        mBundle.putCharSequenceArray(key, value);
        return this;
    }

    public Bundler putBundle(String key, Bundle value) {
        mBundle.putBundle(key, value);
        return this;
    }

    public Bundle get() {
        return mBundle;
    }

    public <T extends Fragment> T into(T fragment) {
        fragment.setArguments(mBundle);
        return fragment;
    }

}
