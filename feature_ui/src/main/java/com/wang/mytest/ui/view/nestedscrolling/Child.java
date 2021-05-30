package com.wang.mytest.ui.view.nestedscrolling;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParentHelper;

public class Child extends View implements NestedScrollingChild {

    private float mDownX;
    private float mDownY;
    private float mMoveX;
    private float mMoveY;
    private float mLastMotionX;
    private float mLastMotionY;

    public Child(Context context) {
        this(context, null);
    }

    public Child(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // 1
        setNestedScrollingEnabled(true);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getRawX();
                mDownY = event.getRawY();
                mLastMotionX = mDownX;
                mLastMotionY = mDownY;
                if (startNestedScroll(SCROLL_AXIS_VERTICAL)) {

                }
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveX = event.getRawX();
                mMoveY = event.getRawY();

                int dx = (int) (mMoveX - mLastMotionX);
                int dy = (int) (mMoveY - mLastMotionY);

                mLastMotionX = mMoveX;
                mLastMotionY = mMoveY;
                int[] consumed = new int[2];
                int[] offsetInWindow = new int[2];
                if (dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)) {

                }

                int dxConsumed = 0;
                int dyConsumed = 0;
                int dxUnconsumed = 0;
                int dyUnconsumed = 0;
                if (dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow)) {

                }
                break;
            case MotionEvent.ACTION_UP:
                stopNestedScroll();
                break;
        }
        return super.onTouchEvent(event);
    }
}
