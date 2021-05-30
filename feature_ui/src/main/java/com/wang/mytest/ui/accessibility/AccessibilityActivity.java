package com.wang.mytest.ui.accessibility;

import android.app.Activity;
import android.os.Bundle;

;
import com.wang.mytest.ui.R;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

public class AccessibilityActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility);

        AccessibilityView accessibilityView = findViewById(R.id.accessibility_view);
        ViewCompat.setAccessibilityDelegate(accessibilityView, new MyAccessibilityDelegate(accessibilityView));
    }
}
