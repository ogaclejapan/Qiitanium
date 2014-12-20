package com.ogaclejapan.qiitanium.presentation;

import com.ogaclejapan.qiitanium.presentation.helper.PicassoHelper;
import com.ogaclejapan.qiitanium.presentation.helper.SimpleActivityLifecycleCallbacks;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                PicassoHelper.class
        },
        complete = false, library = true)
public class PresentationModule {

    @Provides
    Application.ActivityLifecycleCallbacks provideActivityLifecycleCallbacks() {
        return new SimpleActivityLifecycleCallbacks();
    }

}
