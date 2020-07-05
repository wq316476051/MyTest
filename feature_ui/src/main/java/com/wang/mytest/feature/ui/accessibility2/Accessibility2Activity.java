package com.wang.mytest.feature.ui.accessibility2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.wang.mytest.apt.annotation.Route;
import com.wang.mytest.feature.ui.R;
import com.wang.mytest.feature.ui.accessibility.AccessibilityView;
import com.wang.mytest.feature.ui.accessibility.MyAccessibilityDelegate;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

@Route(path = "/activity/ui/accessibility2", title = "Accessibility2")
public class Accessibility2Activity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility2);

        findViewById(R.id.btn_2).setOnClickListener(view -> {
            ((TextView) view).setText("Hello");
            view.setContentDescription("Hello");
        });
    }
}
