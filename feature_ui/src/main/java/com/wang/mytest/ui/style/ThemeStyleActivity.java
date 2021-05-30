package com.wang.mytest.ui.style;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wang.mytest.ui.R;

public class ThemeStyleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_style);

        setTheme(R.style.AppThemeCommon_Immersive);
    }
}
