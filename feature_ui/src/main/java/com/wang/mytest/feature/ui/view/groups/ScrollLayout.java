package com.wang.mytest.feature.ui.view.groups;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class ScrollLayout extends ViewGroup {

    private static final String TAG = "ScrollLayout";

    private Scroller mScroller;

    private int mLastX;

    private int mLeftBoundary;
    private int mRightBoundary;

    public ScrollLayout(Context context) {
        super(context);
        init();
    }

    public ScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(i * getMeasuredWidth(), t, (i + 1) * getMeasuredWidth(), b);
        }

        mLeftBoundary = getChildAt(0).getLeft();
        mRightBoundary = getChildAt(getChildCount() - 1).getRight();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), 0);
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int currentX = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = currentX;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = mLastX - currentX;
                if (getScrollX() + deltaX < mLeftBoundary) {
                    deltaX = mLeftBoundary;
                } else if (getScrollX() + deltaX + getWidth()> mRightBoundary) {
                    deltaX = mRightBoundary - getWidth() - getScrollX();
                }
                scrollTo(getScrollX() + deltaX, 0);
                invalidate();
                mLastX = currentX;
                break;
            case MotionEvent.ACTION_UP:
                int i = (getScrollX() + getWidth() / 2) / getWidth();
                int left = i * getWidth();
                mScroller.startScroll(getScrollX(), 0, left - getScrollX(), 0);
                invalidate();
                break;
        }
        return true;
    }
}
