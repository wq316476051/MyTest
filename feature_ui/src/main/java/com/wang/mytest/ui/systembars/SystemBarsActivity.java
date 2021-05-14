package com.wang.mytest.ui.systembars;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wang.mytest.R;
import com.wang.mytest.common.util.LogUtils;
import com.wang.mytest.common.util.ScreenUtils;

public class SystemBarsActivity extends AppCompatActivity {

    private static final String TAG = "SystemBarsActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_bars);

        Button btnDimSystemBars = findViewById(R.id.btn_dim_sysetm_bars);
        Button btnHideSystemBars = findViewById(R.id.btn_hide_sysetm_bars);
        Button btnImmersiveSystemBars = findViewById(R.id.btn_immsersive_sysetm_bars);
        Button btnResetSystemBars = findViewById(R.id.btn_reset_system_bars);

        btnDimSystemBars.setOnClickListener(this::onDimSystemBars);
        btnHideSystemBars.setOnClickListener(this::onHideSystemBars);
        btnImmersiveSystemBars.setOnClickListener(this::onImmersiveSystemBars);
        btnResetSystemBars.setOnClickListener(this::onResetSystemBars);

        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> {
            LogUtils.d(TAG, "onSystemUiVisibilityChange: " + visibility);
        });
    }

    private void onImmersiveSystemBars(View view) {
        ScreenUtils.immersiveSystemBars(this);
    }

    private void onResetSystemBars(View view) {
        ScreenUtils.resetSystemBars(this);
    }

    /*
    界面标志被清除后（例如，离开 Activity），如果您希望再次隐藏这些栏。
    若要维持，在 onResume / onWindowFocusChanged 中设置标记
     */
    private void onHideSystemBars(View view) {
        ScreenUtils.hideSystemBars(this);
    }

    private void onDimSystemBars(View view) {
        ScreenUtils.dimSystemBars(this);
    }
}
