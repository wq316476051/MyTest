package com.wang.mytest.ui.layout;

import android.os.Bundle;

import com.wang.mytest.apt.annotation.Route;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

@Route(path = "/activity/ui/layout/coordinator", title = "CoordinatorLayout")
public class ViewPager2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
