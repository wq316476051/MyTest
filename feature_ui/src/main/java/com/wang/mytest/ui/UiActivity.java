package com.wang.mytest.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
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

        registerCallback(R.id.btn_print_screen_details, ScreenActivity.class);
        registerCallback(R.id.btn_test_theme_style, ThemeStyleActivity.class);
        registerCallback(R.id.btn_test_state_list_animator, StateListAnimatorActivity.class);
        registerCallback(R.id.btn_test_recycler_view, RecyclerViewActivity.class);
    }

    private void registerCallback(@IdRes int id, Class<? extends Activity> activityClass) {
        final View view = findViewById(id);
        if (view == null) {
            return;
        }
        view.setOnClickListener(v -> {
            Intent intent = new Intent(this, activityClass);
            ActivityUtils.startActivity(this, intent);
        });
    }
}
