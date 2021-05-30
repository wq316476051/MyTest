package com.wang.mytest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wang.mytest.common.util.ActivityUtils;
import com.wang.mytest.ui.animator.StateListAnimatorActivity;
import com.wang.mytest.ui.style.ThemeStyleActivity;
import com.wang.mytest.ui.view.recyclerview.RecyclerViewActivity;

public class UiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);

        findViewById(R.id.btn_print_screen_details).setOnClickListener(this::onPrintScreenDetailsClicked);
        findViewById(R.id.btn_test_theme_style).setOnClickListener(this::onTestThemeStyleClicked);
        findViewById(R.id.btn_test_state_list_animator).setOnClickListener(this::onTestStateListAnimatorClicked);
        findViewById(R.id.btn_test_recycler_view).setOnClickListener(this::onTestRecyclerViewClicked);
    }

    private void onTestRecyclerViewClicked(View view) {
        ActivityUtils.startActivity(this, new Intent(this, RecyclerViewActivity.class));
    }

    private void onPrintScreenDetailsClicked(View view) {
        ActivityUtils.startActivity(this, new Intent(this, ScreenActivity.class));
    }

    private void onTestThemeStyleClicked(View view) {
        ActivityUtils.startActivity(this, new Intent(this, ThemeStyleActivity.class));
    }

    private void onTestStateListAnimatorClicked(View view) {
        ActivityUtils.startActivity(this, new Intent(this, StateListAnimatorActivity.class));
    }
}
