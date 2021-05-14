package com.wang.mytest.ui.accessibility2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.wang.mytest.apt.annotation.Route;
import com.wang.mytest.R;

import androidx.annotation.Nullable;

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
