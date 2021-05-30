package com.wang.mytest.ui.view;

import android.app.Activity;
import android.os.Bundle;

;
import com.wang.mytest.ui.R;
import com.wang.mytest.ui.view.groups.ScrollLayout;

import androidx.annotation.Nullable;

public class ViewActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        ScrollLayout scrollLayout = findViewById(R.id.scroll_layout);
    }
}
