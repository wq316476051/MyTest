package com.wang.mytest.feature.ui.accessibility;

import android.app.Activity;
import android.os.Bundle;

import com.wang.mytest.apt.annotation.Route;
import com.wang.mytest.feature.ui.R;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

@Route(path = "/activity/ui/accessibility", title = "Accessibility")
public class AccessibilityActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility);

        AccessibilityView accessibilityView = findViewById(R.id.accessibility_view);
        ViewCompat.setAccessibilityDelegate(accessibilityView, new MyAccessibilityDelegate(accessibilityView));
    }
}
