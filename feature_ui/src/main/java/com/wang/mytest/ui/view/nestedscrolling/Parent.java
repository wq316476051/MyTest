package com.wang.mytest.ui.view.nestedscrolling;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent;

public class Parent extends LinearLayout implements NestedScrollingParent {

    public Parent(Context context) {
        super(context);
    }

    public Parent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
