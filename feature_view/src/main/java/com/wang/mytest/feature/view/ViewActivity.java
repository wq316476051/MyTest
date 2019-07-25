package com.wang.mytest.feature.view;

import android.app.Activity;
import android.os.Bundle;

import com.wang.mytest.apt.annotation.Route;

import androidx.annotation.Nullable;

@Route(path = "/activity/view")
public class ViewActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
    }
}
