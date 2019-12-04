package com.wang.mytest.feature.ui.view;

import android.app.Activity;
import android.os.Bundle;

import com.wang.mytest.apt.annotation.Route;
import com.wang.mytest.feature.ui.R;
import com.wang.mytest.feature.ui.view.groups.ScrollLayout;

import androidx.annotation.Nullable;

@Route(path = "/activity/ui/view/main", title = "View")
public class ViewActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        ScrollLayout scrollLayout = findViewById(R.id.scroll_layout);
    }
}
