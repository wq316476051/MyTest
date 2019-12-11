package com.wang.mytest.feature.ui.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class MyBehavior extends CoordinatorLayout.Behavior<Button> {

    private int width;

    public MyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        width = context.getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull Button child, @NonNull View dependency) {
        return dependency instanceof TextView;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull Button child, @NonNull View dependency) {
        int x = width - dependency.getLeft() - child.getWidth();
        int y = dependency.getTop();

        setPosition(child, x, y);
        return true;
    }

    private void setPosition(View view, int x, int y) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) layoutParams;
            params.leftMargin = x;
            params.rightMargin = y;
            view.setLayoutParams(params);
        }
    }
}
