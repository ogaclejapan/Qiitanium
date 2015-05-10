package com.ogaclejapan.qiitanium.presentation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ogaclejapan.qiitanium.R;
import com.ogaclejapan.qiitanium.util.IOUtils;
import com.ogaclejapan.rx.binding.Rx;
import com.ogaclejapan.rx.binding.RxActions;
import com.ogaclejapan.rx.binding.RxUtils;
import com.ogaclejapan.rx.binding.RxView;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class LicenseDialogFragment extends AppDialogFragment {

  public static final String TAG = LicenseDialogFragment.class.getSimpleName();
  private static final String LICENSE_PATH = "license.txt";

  private Rx<TextView> licenseText;

  public LicenseDialogFragment() {
    super(TAG);
  }

  public static LicenseDialogFragment newInstance() {
    return new LicenseDialogFragment();
  }

  @Override
  protected View onSetupView(final LayoutInflater inflater, final Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_license, null, false);
    licenseText = RxView.findById(view, R.id.fragment_license_text);
    return view;
  }

  @Override
  protected Subscription onBind() {
    return Subscriptions.from(
        licenseText.bind(loadLicenseHtml(), RxActions.setText())
    );
  }

  private Observable<String> loadLicenseHtml() {
    return Observable.create(new Observable.OnSubscribe<String>() {
      @Override
      public void call(final Subscriber<? super String> subscriber) {
        try {
          final String text = IOUtils.readAllFromAssets(getContext(), LICENSE_PATH);
          RxUtils.onNextIfSubscribed(subscriber, text);
          RxUtils.onCompletedIfSubsribed(subscriber);
        } catch (IOException ioe) {
          RxUtils.onErrorIfSubscribed(subscriber, ioe);
        }
      }
    });
  }

}
