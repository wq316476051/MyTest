package com.wang.mytest;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wang.mytest.common.observer.LocaleChangeObserver;
import com.wang.mytest.common.util.LogUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, MainFragment.newInstance())
                    .commit();
        }

        LocaleChangeObserver.create(this, this)
                .setListener(this::onLocaleChanged)
                .observe();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void onLocaleChanged() {
        LogUtils.debug(TAG, "onLocaleChanged: ");
    }
}