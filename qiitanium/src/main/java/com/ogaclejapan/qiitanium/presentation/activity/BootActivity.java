package com.ogaclejapan.qiitanium.presentation.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class BootActivity extends AppActivity {

    public static void startActivity(Context context) {
        context.startActivity(intentof(context));
    }

    public static Intent intentof(Context context) {
        return new Intent(context, BootActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO ログイン初期化処理など追加予定

        TopActivity.startActivity(this);
    }

}
